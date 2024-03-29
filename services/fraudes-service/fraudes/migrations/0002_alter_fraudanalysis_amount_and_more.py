# Generated by Django 4.1.11 on 2023-09-30 00:49

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('fraudes', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='fraudesanalysis',
            name='amount',
            field=models.FloatField(blank=True),
        ),
        migrations.AlterField(
            model_name='fraudesanalysis',
            name='deny_reason',
            field=models.CharField(blank=True, max_length=200),
        ),
        migrations.AlterField(
            model_name='fraudesanalysis',
            name='fraudes_score',
            field=models.FloatField(blank=True, null=True),
        ),
    ]
