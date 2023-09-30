from rest_framework import generics
from .models import fraudesAnalysis
from django.http import HttpResponse

from .serializers import fraudesAnalysisSerializer

class fraudesAnalysisList(generics.ListCreateAPIView):
	queryset = fraudesAnalysis.objects.all()
	serializer_class = fraudesAnalysisSerializer
	
class fraudesCreateAnalysis(generics.CreateAPIView):
	queryset = fraudesAnalysis.objects.all()
	serializer_class = fraudesAnalysisSerializer


def health(request):
		return HttpResponse('OK')