from rest_framework.views import APIView
from rest_framework.response import Response
from .models import *
from django.utils import timezone
from .someFuncs import *

def fixtime(s):
    x = s.find('.')
    if x == -1:
        return s
    return s[:x]

class DealFindMissions(APIView):
    @staticmethod
    def post(request, user_ID):
        return Response("fuckyou!")

class DealFindMessages(APIView):
    @staticmethod
    def post(request, user_ID):
        return Response("fuckyou!")

class DealFindEvaluations(APIView):
    @staticmethod
    def post(request, user_ID):
        return Response("fuckyou!")

class DealFindUsers(APIView):
    @staticmethod
    def post(request, getter_ID, mission_ID):
        return Response("fuckyou!")
