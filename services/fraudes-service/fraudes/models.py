from django.db import models

OPERATION_TYPE = (
    ("deposit", "deposit"),
    ("sacar", "sacar"),
    ("pagar", "pagar"),
    ("transferir", "transferir"),
)

ANALYSIS_STATUS = (
    ("PENDING", "PENDING"),
    ("FINISHED", "FINISHED"),
)


class FraudesAnalysis(models.Model):
    operation_type = models.CharField(max_length=20, choices=OPERATION_TYPE)
    amount = models.FloatField(blank=True)
    status = models.CharField(max_length=20, choices=ANALYSIS_STATUS)
    fraudes_score = models.FloatField(blank=True, null=True)
    deny_reason = models.CharField(max_length=200, blank=True)
    cliente_id = models.CharField(max_length=20)
    session_id = models.CharField(max_length=20)

    def __str__(self):
        return self.operation_type

    def finish_analysis(self, fraudes_score: float, deny_reason: str):
        self.status = "FINISHED"
        self.fraudes_score = fraudes_score
        self.deny_reason = deny_reason
        self.save()
