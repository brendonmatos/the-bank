from rest_framework import generics
from .models import FraudesAnalysis
from django.http import HttpResponse

from .serializers import FraudesAnalysisSerializer


class FraudesAnalysisList(generics.ListCreateAPIView):
    queryset = FraudesAnalysis.objects.all()
    serializer_class = FraudesAnalysisSerializer


class FraudesCreateAnalysis(generics.CreateAPIView):
    queryset = FraudesAnalysis.objects.all()
    serializer_class = FraudesAnalysisSerializer


def health(request):
    return HttpResponse("OK")
