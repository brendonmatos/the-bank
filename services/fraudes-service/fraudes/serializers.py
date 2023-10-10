from rest_framework import serializers
from .models import FraudesAnalysis

class FraudesAnalysisSerializer(serializers.ModelSerializer):
	class Meta:
		model = FraudesAnalysis
		fields = '__all__'

	