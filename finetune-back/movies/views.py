from rest_framework.response import Response
from rest_framework.decorators import api_view
# Authentication Decorators
from rest_framework.decorators import authentication_classes

# permission Decorators
from rest_framework.decorators import permission_classes
from rest_framework.permissions import IsAuthenticated, AllowAny

from rest_framework import status
from django.shortcuts import get_object_or_404, get_list_or_404
from .serializers import MovieListSerializer, MovieSerializer, GenreSerializer
from .serializers import IdealMovieSerializer, Quiz1Serializer, Quiz2Serializer
from .models import Genre, Movie, Quiz1, Quiz2
# from .models import Ideal
import random
from django.db.models import Count
from django.contrib.auth import get_user_model

# Create your views here.
######################
# Home에 쓰일 영화 데이터 제공
# 영화 전체 데이터, 검색기능 구현 완료.
@api_view(['GET'])
def movie_list(request):
    if request.method == 'GET':
        search_query = request.GET.get('query')
        if search_query:
            # 검색어에서 공백 제거
            search_query = search_query.replace(" ", "")
            keywords = search_query.split()
            movies = Movie.objects.all()
            # movies = get_list_or_404(Movie)
            filtered_movies = []
            for movie in movies:
                title = movie.title.replace(" ", "")
                matched = True
                for keyword in keywords:
                    if keyword.lower() not in title.lower():
                        matched = False
                        break
                if matched:
                    filtered_movies.append(movie)
            serializer = MovieListSerializer(filtered_movies, many=True)
            return Response(serializer.data)
        else:
            movies = get_list_or_404(Movie)
            random.shuffle(movies)
            serializer = MovieListSerializer(movies, many=True)
            return Response(serializer.data)
    return Response(status=status.HTTP_400_BAD_REQUEST)


# 영화 전체 데이터 평점 순 정렬
@api_view(['GET'])
def vote_average_sort(request):
    if request.method == 'GET':
        movies = Movie.objects.filter(vote_count__gte=1000).order_by('-vote_average')[:50]
        movies = random.sample(list(movies), 30)
        serializer = MovieListSerializer(movies, many=True)
        return Response(serializer.data)
    return Response(status=status.HTTP_400_BAD_REQUEST)

# 영화 전체 데이터 개봉일 순 정렬
@api_view(['GET'])
def release_date_sort(request):
    if request.method == 'GET':
        movies = list(Movie.objects.order_by('-release_date')[:30])  # 개봉일 순으로 정렬
        random.shuffle(movies)
        serializer = MovieListSerializer(movies, many=True)
        return Response(serializer.data)
    return Response(status=status.HTTP_400_BAD_REQUEST)

# 영화 전체 데이터 인기 순 정렬
@api_view(['GET'])
def popularity_sort(request):
    if request.method == 'GET':
        movies = list(Movie.objects.order_by('-popularity')[:30])  # 인기 순으로 정렬
        random.shuffle(movies)
        serializer = MovieListSerializer(movies, many=True)
        return Response(serializer.data)
    return Response(status=status.HTTP_400_BAD_REQUEST)
        
# 장르별 영화 데이터 구현.(모든 장르의 데이터를 한 번에 보내주기)
@api_view(['GET'])
def movies_by_genre(request):
    if request.method == 'GET':
        genres = [12, 14, 16, 18, 27, 28, 35, 36, 37, 53, 80, 99, 878, 9648, 10402, 10749, 10751, 10752, 10770]
        genres_all = []
        for genre_id in genres:
            genre = get_object_or_404(Genre, pk=genre_id)
            movies = list(genre.movie_set.all())
            if len(movies) > 30:
                movies = random.sample(movies, 30)          # 30개의 영화만 선택
            else:
                random.shuffle(movies)                      # 영화 리스트를 무작위로 섞음
            serializer = MovieListSerializer(movies, many=True)
            genres_all.append(serializer.data)
        return Response(genres_all)
    return Response(status=status.HTTP_404_NOT_FOUND)


######################
# 영화 세부데이터로 쓰일 함수
# 영화 상세 데이터
@api_view(['GET'])
def movie_detail(request, movie_pk):
    # article = Article.objects.get(pk=article_pk)
    movie = get_object_or_404(Movie, pk=movie_pk)

    if request.method == 'GET':
        serializer = MovieSerializer(movie)
        user = request.user
        if movie.like_users.filter(pk=user.pk).exists():
            is_liked = True
        else:
            is_liked = False
        data = serializer.data
        data['is_liked'] = is_liked
        return Response(data)

# 장르 데이터
@api_view(['GET'])
def get_genre(request):
    if request.method == 'GET':
        genres = get_list_or_404(Genre)
        serializer = GenreSerializer(genres, many=True)
        return Response(serializer.data)

######################

# 좋아요 기능
@api_view(['POST'])
@permission_classes([IsAuthenticated])
def movie_like(request, movie_pk):
    movie = get_object_or_404(Movie, pk=movie_pk)
    user = request.user
    is_liked = movie.like_users.filter(id = user.id).exists()

    if is_liked :
        movie.like_users.remove(request.user)
        is_liked = False
    else:
        movie.like_users.add(request.user)
        is_liked = True
    like_users_num = movie.like_users.count()
    context = {
        'like_users_num':like_users_num,
        'is_liked': is_liked,
        'movie_title': movie.title
    }
    return Response(context, status=status.HTTP_200_OK)

########################
# 영화 추천
# 무작위 영화 추천 기능
@api_view(['GET'])
def recommend(request):
    if request.method == 'GET':
        # 영화 전체 데이터에서 30개 뽑아서 줌
        movies = get_list_or_404(Movie)
        movies_recommend = random.sample(movies, 30)
        serializer = MovieListSerializer(movies_recommend, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)

# 좋아요 한 영화와 이상형 월드컵에서 이긴 영화를 기반으로 영화 추천해줌.
@api_view(['GET'])
def recommend_custom(request):
    if request.method == 'GET':
        if request.user.is_authenticated:
            print('로그인됨')
            genres_all = []
            if request.user.idealmovie.exists():
                for movie in request.user.idealmovie.all():
                    category = movie.genres.all()
                    genres_all.extend(category)
                    

            if request.user.like_movies.exists():
                for movie in request.user.like_movies.all():
                    categories = movie.genres.all()
                    genres_all.extend(categories)

            from collections import Counter
            genre_counts = Counter(genres_all)
            if len(genre_counts) > 0:
                sorted_counts = sorted(genre_counts.items(), key=lambda x: x[1], reverse=True)
                result = {genre.id: count for genre, count in sorted_counts}
                max_genre_id = list(result.keys())[0]
                genre = Genre.objects.get(pk=max_genre_id)
                movies = genre.movie_set.all()
                movies = list(movies)
                random.shuffle(movies)
                if len(movies) >= 30:
                    movies = random.sample(movies, 30)
                    random.shuffle(movies)
                elif len(movies) < 30:
                    need_num = 30 - len(movies)
                    excluded_genre = Genre.objects.get(pk=max_genre_id)
                    movies_filtered = Movie.objects.exclude(genres=excluded_genre)
                    sample_movies = random.sample(list(movies_filtered), need_num)
                    random.shuffle(movies)
                    movies += sample_movies
                print('받음')
                serializer = MovieSerializer(movies, many=True)
                return Response(serializer.data, status=status.HTTP_200_OK)
        print('못받음')
        # 이상영화와 좋아요한 영화가 없거나 로그인이 안되어 있을 경우, 아무 영화나 60개 추천
        movies = get_list_or_404(Movie)
        movies_recommend = random.sample(movies, 30)
        serializer = MovieSerializer(movies_recommend, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)
    return Response(status=status.HTTP_400_BAD_REQUEST)

######################
# 영화 이상형 월드컵 기능

# @api_view(['GET'])
@api_view(['GET', 'POST'])
@permission_classes([IsAuthenticated])
def ideal_movie(request):
    if request.user.is_authenticated:
        # 최초 게임을 위한 64개의 데이터를 던져줌.
        if request.method == 'GET':
            movies = get_list_or_404(Movie)
            ideal_movies = random.sample(movies, 64)
            serializer = IdealMovieSerializer(ideal_movies, many=True)
            return Response(serializer.data, status=status.HTTP_200_OK)
        # 최종 승리 영화 DB에 입력받기
        # 참고: 이건 되는지 확인 못함......
        elif request.method == 'POST':
            movie_pk = request.POST.get('movie_id')
            person = get_user_model().objects.get(pk=request.user.pk)
            person.idealmovie.add(movie_pk)
            return Response({'message': f'Movie ID: {movie_pk}'}, status=status.HTTP_201_CREATED)
        return Response(status=status.HTTP_400_BAD_REQUEST)
    return Response(status=status.HTTP_401_UNAUTHORIZED)

    
# 최종 승리 데이터 vue에서 받아옴. 마치 like 구현과 같음.
# 작동 잘 됨 확인 완료
# 해당 주소로 연결 시에 작동 함path('ideal_movie/<int:movie_pk>/', views.win_ideal_movie),
# @api_view(['POST'])
# @permission_classes([IsAuthenticated])
# def win_ideal_movie(request, movie_pk):
#     if request.method == 'POST':
#         person = get_user_model().objects.get(pk=request.user.pk)
#         person.idealmovie.add(movie_pk)
#         return Response(status=status.HTTP_201_CREATED)
#     return Response(status=status.HTTP_400_BAD_REQUEST)


########################
# 영화 퀴즈
# 영화 퀴즈(3지선다)
@api_view(['GET', 'POST'])
@permission_classes([IsAuthenticated])
def quiz1(request):
    if request.user.is_authenticated:
        user = request.user
        # user 포인트 확인
        
        if request.method == 'GET':
            if user.point < 50:
                context = {
                "question" : "포인트가 충분하지 않습니다."
                }
                return Response(context)
            quizzes = get_list_or_404(Quiz1)
            quiz = random.sample(quizzes, 1)[0]
            options = quiz.options
            random.shuffle(options)
            serializer = Quiz1Serializer(quiz)
            return Response(serializer.data, status=status.HTTP_200_OK)
        
        ##### 아래 POST 부분은 손볼 필요성 있음 ####
        elif request.method == 'POST':
            solve = request.data.get('solve')
            quiz_id = request.data.get('quiz_id')       # post로 받아오게될 quiz1의 id를 받아냄
            
            quiz = get_object_or_404(Quiz1, pk=quiz_id)
            print(quiz.answer)
            if solve == quiz.answer:
                # user경험치 및 포인트 증가
                user.exp += 100
                user.point += 100
                user.save()
                return Response({"message": "정답입니다. 100포인트를 얻습니다!"},status=status.HTTP_200_OK)
            else:
                user.point -= 50
                user.save()
                return Response({"message": "오답입니다!"},status=status.HTTP_200_OK)
        return Response(status=status.HTTP_400_BAD_REQUEST)
    return Response(status=status.HTTP_401_UNAUTHORIZED)

# 영화 퀴즈(OX)
@api_view(['GET', 'POST'])
@permission_classes([IsAuthenticated])
def quiz2(request):
    if request.user.is_authenticated:
        user = request.user
        
        if request.method == 'GET':
            if user.point < 50:
                context = {
                "question" : "포인트가 충분하지 않습니다."
                }
                return Response(context)
            quizzes = get_list_or_404(Quiz2)
            quiz = random.sample(quizzes, 1)
            serializer = Quiz2Serializer(quiz, many=True)
            # user포인트 차감
            return Response(serializer.data, status=status.HTTP_200_OK)
        
        ##### 아래 POST 부분은 손볼 필요성 있음 ####
        elif request.method == 'POST':
            solve = request.data.get('solve')
            quiz_id = request.data.get('quiz_id')       # post로 받아오게될 quiz2의 id를 받아냄
            
            quiz = get_object_or_404(Quiz2, pk=quiz_id)
            
            if solve == quiz.answer:
                # user경험치 및 포인트 증가
                user.exp += 100
                user.point += 100
                user.save()
                return Response({"message": "정답입니다. 100포인트를 얻습니다!"},status=status.HTTP_200_OK)
            else:
                user.point -= 50
                user.save()
                return Response({"message": "오답입니다!"},status=status.HTTP_200_OK)
        return Response(status=status.HTTP_400_BAD_REQUEST)
    return Response(status=status.HTTP_401_UNAUTHORIZED)

######################