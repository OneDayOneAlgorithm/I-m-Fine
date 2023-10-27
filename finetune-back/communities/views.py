from rest_framework.response import Response
from rest_framework.decorators import api_view
# Authentication Decorators
from rest_framework.decorators import authentication_classes

# permission Decorators
from rest_framework.decorators import permission_classes
from rest_framework.permissions import IsAuthenticated

from rest_framework import status
from django.shortcuts import get_object_or_404, get_list_or_404
from .serializers import ReviewListSerializer, ReviewSerializer, CommentSerializer, AnonyarticleListSerializer, AnonyarticleSerializer, AnonycommentSerializer
from .models import Review, Comment, Anonyarticle, Anonycomment

# Create your views here.

# 영화 리뷰 게시글 및 댓글
# 영화 리뷰 전체 불러오기 및 게시글 생성
@api_view(['GET', 'POST'])
# @permission_classes([IsAuthenticated])
def review_list(request):
    if request.method == 'GET':
        # articles = Article.objects.all()
        reviews = Review.objects.all().order_by('-id')
        serializer = ReviewListSerializer(reviews, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        if request.user.is_authenticated:
            serializer = ReviewSerializer(data=request.data)
            if serializer.is_valid(raise_exception=True):
                # 게시글 생성으로 사용자의 exp 및 point 를 200 증가 시키기
                user = request.user
                user.exp += 200
                user.point += 200
                user.save()
                print('경험치 200 증가!')
                serializer.save(user=request.user)            
                return Response(serializer.data, status=status.HTTP_201_CREATED)
            return Response(status=status.HTTP_400_BAD_REQUEST)
        return Response(status=status.HTTP_401_UNAUTHORIZED)

# 영화 리뷰 게시글 상세보기
@api_view(['GET', 'DELETE', 'PUT'])
@permission_classes([IsAuthenticated])
def review_detail(request, review_pk):
    review = get_object_or_404(Review, pk=review_pk)

    if request.method == 'GET':
        serializer = ReviewSerializer(review)
        
        user = request.user
        if review.like_users.filter(pk=user.pk).exists():
            is_liked = True
        else:
            is_liked = False
        
        if request.user == review.user:
            same_user = True
        else:
            same_user = False
        data = serializer.data
        data['same_user'] = same_user
        data['is_liked'] = is_liked
        return Response(data)            
    
    elif request.method == 'DELETE':
        if request.user == review.user:
            review.delete()
            return Response(status=status.HTTP_204_NO_CONTENT)
        return Response(status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'PUT':
        if request.user == review.user:
            serializer = ReviewSerializer(review, data=request.data)
            if serializer.is_valid(raise_exception=True):
                serializer.save()
                return Response(serializer.data)
        return Response(status=status.HTTP_400_BAD_REQUEST)


# 댓글 목록 불러오기
@api_view(['GET'])
@permission_classes([IsAuthenticated])
def comment_list(request):
    if request.method == 'GET':
        # comments = Comment.objects.all()
        comments = get_list_or_404(Comment)
        serializer = CommentSerializer(comments, many=True)
        return Response(serializer.data)

# 댓글 상세보기

@api_view(['GET', 'DELETE', 'PUT'])
@permission_classes([IsAuthenticated])
def comment_detail(request, comment_pk):
    comment = get_object_or_404(Comment, pk=comment_pk)

    if request.method == 'GET':
        serializer = CommentSerializer(comment)
        return Response(serializer.data)

    elif request.method == 'DELETE':
        if request.user == comment.user:
            comment.delete()
            return Response(status=status.HTTP_204_NO_CONTENT)
        return Response(status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'PUT':
        if request.user == comment.user:
            serializer = CommentSerializer(comment, data=request.data)
            if serializer.is_valid(raise_exception=True):
                serializer.save()
                return Response(serializer.data)
        return Response(status=status.HTTP_400_BAD_REQUEST)

# 댓글 생성

@api_view(['POST'])
@permission_classes([IsAuthenticated])
def comment_create(request, review_pk):
    review = get_object_or_404(Review, pk=review_pk)
    serializer = CommentSerializer(data=request.data)
    if serializer.is_valid(raise_exception=True):
        # 댓글 생성으로 사용자의 exp 및 point를 50 증가 시키기
        user = request.user
        user.exp += 50
        user.point += 50
        user.save()
        print('경험치 50 증가!')
        serializer.save(review=review, user=request.user)
        return Response(serializer.data, status=status.HTTP_201_CREATED)

# review 게시글 좋아요 하기
@api_view(['POST'])
@permission_classes([IsAuthenticated])
def review_like(request, review_pk):
    review = get_object_or_404(Review, pk=review_pk)
    user = request.user
    if review.like_users.filter(pk=user.pk).exists():
        review.like_users.remove(user)
        is_liked = False
    else:
        review.like_users.add(user)
        is_liked = True    
    
    review.like_users.count()
    like_users_num = review.like_users.count()
    context = {
        'like_users_num':like_users_num,
        'is_liked' : is_liked
    }
    return Response(context, status=status.HTTP_200_OK)
# 익명 게시글 및 리뷰
# 익명 게시글 목록 불러오기 및 생성
@api_view(['GET', 'POST'])
# @permission_classes([IsAuthenticated])
def anonyarticle_list(request):
    if request.method == 'GET':
        anonyarticle = Anonyarticle.objects.all().order_by('-id')
        serializer = AnonyarticleListSerializer(anonyarticle, many=True)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = AnonyarticleSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            if request.user.is_authenticated:
                # 게시글 생성으로 사용자의 exp 및 point 를 50 증가 시키기
                user = request.user
                user.exp += 50
                user.point += 50
                user.save()
                print('경험치 50 증가!')
            return Response(serializer.data, status=status.HTTP_201_CREATED)

# 익명 게시글 상세보기
@api_view(['GET', 'DELETE', 'PUT'])
def anonyarticle_detail(request, anonyarticle_pk):
    anonyarticle = get_object_or_404(Anonyarticle, pk=anonyarticle_pk)
    if request.method == 'GET':
        serializer = AnonyarticleSerializer(anonyarticle)
        return Response(serializer.data)
    
    elif request.method == 'DELETE':
        password = request.data.get('password')
        if anonyarticle.check_password(password):
            anonyarticle.delete()
            return Response(status=status.HTTP_204_NO_CONTENT)
        return Response(status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'PUT':
        # anonyarticle의 비밀번호가 입력하는 비밀번호와 같을때만 작동하게 하기!
        password = request.data.get('password')
        if anonyarticle.check_password(password):
            serializer = AnonyarticleSerializer(anonyarticle, data=request.data)
            if serializer.is_valid(raise_exception=True):
                serializer.save()
                return Response(serializer.data)
        return Response(status=status.HTTP_400_BAD_REQUEST)

# 익명 댓글 목록
@api_view(['GET'])
def anonycomment_list(request):
    if request.method == 'GET':
        anonycomments = get_list_or_404(Anonycomment)
        serializer = AnonycommentSerializer(anonycomments, many=True)
        return Response(serializer.data)

# 익명 댓글 상세보기
@api_view(['GET', 'DELETE', 'PUT'])
def anonycomment_detail(request, anonycomment_pk):
    anonycomment = get_object_or_404(Anonycomment, pk=anonycomment_pk)

    if request.method == 'GET':
        serializer = AnonycommentSerializer(anonycomment)
        return Response(serializer.data)

    elif request.method == 'DELETE':
        password = request.data.get('password')
        if anonycomment.check_password(password):
            anonycomment.delete()
            return Response(status=status.HTTP_204_NO_CONTENT)
        return Response(status=status.HTTP_400_BAD_REQUEST)

    elif request.method == 'PUT':
        # anonycomment의 비밀번호가 입력하는 비밀번호와 같을때만 작동하게 하기!
        password = request.data.get('password')
        if anonycomment.check_password(password):
            serializer = AnonycommentSerializer(anonycomment, data=request.data)
            if serializer.is_valid(raise_exception=True):
                serializer.save()
                return Response(serializer.data)
        return Response(status=status.HTTP_400_BAD_REQUEST)

# 익명 댓글 생성
@api_view(['POST'])
def anonycomment_create(request, anonyarticle_pk):
    anonyarticle = get_object_or_404(Anonyarticle, pk=anonyarticle_pk)

    if request.method == 'POST':
        serializer = AnonycommentSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save(anonyarticle=anonyarticle)
            if request.user.is_authenticated:
                # 게시글 생성으로 사용자의 exp 및 point 를 10 증가 시키기
                user = request.user
                user.exp += 10
                user.point += 10
                user.save()
                print('경험치 10 증가!')
            return Response(serializer.data, status=status.HTTP_201_CREATED)
    return Response(status=status.HTTP_400_BAD_REQUEST)