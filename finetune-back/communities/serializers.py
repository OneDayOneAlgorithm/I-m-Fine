from rest_framework import serializers
from .models import Review, Comment, Anonyarticle, Anonycomment

# 영화 리뷰 게시판
# 영화 리뷰 목록
class ReviewListSerializer(serializers.ModelSerializer):
    username = serializers.CharField(source='user.username', read_only=True)
    movie_title = serializers.CharField(source='movie.title', read_only=True)

    class Meta:
        model = Review
        fields = ('id', 'title', 'content', 'user', 'username','movie', 'movie_title',)

# 영화 리뷰의 댓글
class CommentSerializer(serializers.ModelSerializer):
    username = serializers.CharField(source='user.username', read_only=True)
    class Meta:
        model = Comment
        # fields = '__all__'
        fields = ('id', 'content','review','user','username','created_at')
        read_only_fields = ('review','user', 'usename',)

# 영화 리뷰 상세
class ReviewSerializer(serializers.ModelSerializer):
    comment_set = CommentSerializer(many=True, read_only=True)
    comment_count = serializers.IntegerField(source='comment_set.count', read_only=True)
    username = serializers.CharField(source='user.username', read_only=True)
    movie_title = serializers.CharField(source='movie.title', read_only=True)

    class Meta:
        model = Review
        fields = '__all__'
        read_only_fields = ('user', )


##################################################################

# 익명 게시판
# 익명게시글 목록
class AnonyarticleListSerializer(serializers.ModelSerializer):
    class Meta:
        model = Anonyarticle
        fields = '__all__'

# 익명게시글의 댓글
class AnonycommentSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)   # 작성 시에만 password가 보이게
    
    class Meta:
        model = Anonycomment
        fields = '__all__'
        read_only_fields = ('anonyarticle',)

# 영화 리뷰 상세
class AnonyarticleSerializer(serializers.ModelSerializer):
    anonycomment_set = AnonycommentSerializer(many=True, read_only=True)
    anonycomment_count = serializers.IntegerField(source='anonycomment_set.count', read_only=True)
    password = serializers.CharField(write_only=True)   # 작성 시에만 password가 보이게

    class Meta:
        model = Anonyarticle
        fields = '__all__'
        # read_only_fields = ('user', )