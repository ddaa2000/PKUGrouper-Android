from rest_framework.views import APIView
from rest_framework.response import Response
from .models import *
from .mission import *
from .user import *
from .message import to_json as message_to_json
from django.utils import timezone
from .someFuncs import *


class DealFindMissions(APIView):
    @staticmethod
    def post(request, user_ID):
        uid = checkUID(request.data)
        if uid is 0:
            return Response("Unauthorized", 401)
        elif User.objects.filter(id=user_ID).count() == 0:
            return Response("User Not Found", 404)
        elif uid != user_ID:
            return Response("Unauthorized", 401)
        else:
            missions = request.data.get('missionIDs', [])
            if len(missions) == 0:
                return Response("Bad request", 400)
            else:
                missions.sort()
                mission_detail = []
                for mission_id in missions:
                    if Mission.objects.filter(mission_id).count() == 0:
                        return Response("mission Not Found", 404)
                    else:
                        mission_detail.append(mission_to_json(Mission.objects.get(id=mission_id)))
                response = Response(mission_detail, 200)
        return response


class DealFindMessages(APIView):
    @staticmethod
    def post(request, user_ID):
        uid = checkUID(request.data)
        if uid is 0:
            return Response("Unauthorized", 401)
        elif User.objects.filter(id=user_ID).count() == 0:
            return Response("User Not Found", 404)
        elif uid != user_ID:
            return Response("Unauthorized", 401)
        else:
            messages = request.data.get('messageIDs', [])
            if len(messages) == 0:
                return Response("Bad request", 400)
            else:
                messages.sort()
                message_detail = []
                for message_id in messages:
                    if Message.objects.filter(message_id).count() == 0:
                        return Response("message Not Found", 404)
                    else:
                        message_detail.append(message_to_json(Message.objects.get(id=message_id)))
                response = Response(message_detail, 200)
        return response


class DealFindEvaluations(APIView):
    @staticmethod
    def post(request, user_ID):
        uid = checkUID(request.data)
        if uid is 0:
            return Response("Unauthorized", 401)
        elif User.objects.filter(id=user_ID).count() == 0:
            return Response("User Not Found", 404)
        elif uid != user_ID:
            return Response("Unauthorized", 401)
        else:
            evaluations = request.data.get('evaluationIDs', [])
            if len(evaluations) == 0:
                return Response("Bad request", 400)
            else:
                evaluations.sort()
                evaluation_detail = []
                for evaluation_id in evaluations:
                    if Evaluation.objects.filter(evaluation_id).count() == 0:
                        return Response("evaluation Not Found", 404)
                    else:
                        evaluation_detail.append(evaluation_to_json(Evaluation.objects.get(id=evaluation_id)))
                response = Response(evaluation_detail, 200)
        return response


class DealFindUsers(APIView):
    @staticmethod
    def post(request, getter_ID, mission_ID):
        
        return Response("fuckyou!")
