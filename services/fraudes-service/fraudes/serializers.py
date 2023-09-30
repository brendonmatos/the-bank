from rest_framework import serializers
from .models import fraudesAnalysis

class fraudesAnalysisSerializer(serializers.ModelSerializer):
	class Meta:
		model = fraudesAnalysis
		fields = '__all__'

	