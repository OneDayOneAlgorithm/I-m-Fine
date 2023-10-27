from rest_framework.response import Response
from rest_framework.decorators import api_view
# Authentication Decorators
from rest_framework.decorators import authentication_classes

# permission Decorators
from rest_framework.decorators import permission_classes
from rest_framework.permissions import IsAuthenticated

from rest_framework import status
from django.shortcuts import get_object_or_404, get_list_or_404
from .serializers import FollowSerializer, CustomUserSerializer
# from .models import User
from django.contrib.auth import get_user_model

# Create your views here.

# @authentication_classes([IsAuthenticated])
# @api_view(['POST'])
# def follow_user(request, user_pk):
#     user_to_follow = get_object_or_404(User, pk=user_pk)
#     user = request.user

#     if user_to_follow == user:
#         return Response({'error': 'You cannot follow yourself.'}, status=400)

#     if user in user_to_follow.followers.all():
#         user_to_follow.followers.remove(user)
#         return Response({'status': 'success', 'message': 'Unfollowed successfully.'})

#     user_to_follow.followers.add(user)
#     return Response({'status': 'success', 'message': 'Followed successfully.'})


# @login_required # 로그인 성공후에 redirect >> GET 요청
@api_view(['POST'])
@permission_classes([IsAuthenticated])
def follow(request, user_pk):
    # 현재 로그인한 사용자가 user_pk 사용자를 follow/unfollow 하기
    if request.user.is_authenticated:
        person = get_user_model().objects.get(pk=user_pk)
        if request.user != person:
            if request.user in person.followers.all():
                person.followers.remove(request.user)
            else:
                person.followers.add(request.user)
            serializer = FollowSerializer(person, context={'request':request})
            data = serializer.data
            data['is_follow'] = request.user in person.followers.all()
            # return Response(serializer.data, status=status.HTTP_202_ACCEPTED)
            return Response(data, status=status.HTTP_202_ACCEPTED)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)
    else:
        return Response(status=status.HTTP_401_UNAUTHORIZED)


@api_view(['POST'])
@permission_classes([IsAuthenticated])
def unregister(request):
# 현재 로그인한 사용자만 회원 탈퇴 할 수 있음.
    if request.user.is_authenticated:
        persons = get_user_model().objects.all() 
        person = get_user_model().objects.get(pk=request.user.pk)
        if request.user == person:
            if person in persons:
                person.delete()
            return Response(status=status.HTTP_200_OK)
        else:
            return Response(status=status.HTTP_400_BAD_REQUEST)
    else:
        return Response(status=status.HTTP_401_UNAUTHORIZED)

@api_view(['GET'])
@permission_classes([IsAuthenticated])
def profile(request, user_pk):
    if request.user.is_authenticated:
        # person = get_user_model().objects.get(pk=user_pk)
        person = get_object_or_404(get_user_model(), pk=user_pk)
        serializer = CustomUserSerializer(person, context={'request':request})
        data = serializer.data
        data['is_follow'] = request.user in person.followers.all()

        return Response(data, status=status.HTTP_200_OK)
    else:
        return Response(status=status.HTTP_401_UNAUTHORIZED)
    
    
@api_view(['GET'])
@permission_classes([IsAuthenticated])
def myprofile(request):
    if request.user.is_authenticated:
        # person = get_user_model().objects.get(pk=user_pk)
        user = request.user
        person = get_object_or_404(get_user_model(), pk=user.pk)
        serializer = CustomUserSerializer(person)
        return Response(serializer.data, status=status.HTTP_200_OK)
    else:
        return Response(status=status.HTTP_401_UNAUTHORIZED)