# from drf_spectacular.views import SpectacularAPIView, SpectacularSwaggerView
from django.urls import path
from . import views


urlpatterns = [
    # 영화 리뷰 게시판
    # path('communities/', include('communities.urls')),
    path('', views.review_list),
    path('<int:review_pk>/', views.review_detail),
    path('comments/', views.comment_list),
    path('comments/<int:comment_pk>/', views.comment_detail),
    path('<int:review_pk>/comments/', views.comment_create),
    path('<int:review_pk>/like/', views.review_like),
    
    # 익명 게시판
    path('anonymous/', views.anonyarticle_list),
    path('anonymous/<int:anonyarticle_pk>/', views.anonyarticle_detail),
    path('anonymous/anonycomments/', views.anonycomment_list),
    path('anonymous/anonycomments/<int:anonycomment_pk>/', views.anonycomment_detail),
    path('anonymous/<int:anonyarticle_pk>/anonycomments/', views.anonycomment_create),
]