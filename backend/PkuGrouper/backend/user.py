'''
written by zephyr
2020/12/6

PkuGrouper Backend part: user request
'''
from rest_framework.views import APIView
from rest_framework.response import Response
from .models import *
from django.utils import timezone
from .someFuncs import *
import datetime
import json


def list2str(a):
    result = "["
    for i in range(len(a)):
        result += str(a[i])
        if i is not len(a) - 1:
            result += ", "
    result += "]"
    return result


class DealSelf(APIView):  # 获取个人信息
    @staticmethod
    def get(request, user_ID):
        uid = checkUID(request.data)
        if uid is 0:
            response = Response("Unauthorized", 401)
        elif User.objects.filter(id=user_ID).count() == 0:
            response = Response("User Not Found", 404)
        else:
            user = User.objects.get(id=uid)
            mailbox = user.mailbox
            username = user.username
            tele = user.tele
            missions = []
            for mission in user.missionsAsPublisher.all():
                missions.append(mission.id)
            for mission in user.missionsAsMember.all():
                missions.append(mission.id)
            evaluations = []
            score = 0
            for evaluation in user.evaluationsAsEvaluatee.all():
                evaluations.append(evaluation.id)
                score += evaluation.evaluationScore
            if user.evaluationsAsEvaluatee.all().count() is not 0:
                score /= user.evaluationsAsEvaluatee.all().count()
            violations = []
            for message in user.messagesAsReportee.all():
                violations.append(message.id)
            data = {'mailbox': mailbox, 'username': username, 'tele': tele, 'missionIDs': missions,
                    'evaluationIDs': evaluations, 'violationIDs': violations, 'averageScore': score}
            response = Response(json.dumps(data), 200)
        return response


class DealMember(APIView):  # 获取他人信息
    @staticmethod
    def get(request, getter_ID, mission_ID, gettee_ID):  # geter是获取人，getee是被获取人
        uid = checkUID(request.data)
        if uid is 0:
            response = Response("Unauthorized", 401)
        elif User.objects.filter(id=getter_ID).count() == 0:
            response = Response("getter Not Found", 404)
        elif Mission.objects.filter(id=mission_ID).count() == 0:
            response = Response("mission Not Found", 404)
        elif User.objects.filter(id=gettee_ID).count() == 0:
            response = Response("gettee Not Found", 404)
        elif gettee_ID == getter_ID:
            response = Response("Bad Request", 400)
        elif Mission.objects.get(id=mission_ID).members.filter(id=gettee_ID).count() is 0 \
                and Mission.objects.get(id=mission_ID).publisher.id is not gettee_ID:
            response = Response("Forbidden", 403)
        else:
            getter = User.objects.get(id=getter_ID)
            gettee = User.objects.get(id=gettee_ID)
            score = 0
            for evaluation in gettee.evaluationsAsEvaluatee.all():
                score += evaluation.evaluationScore
            if gettee.evaluationsAsEvaluatee.all().couunt() is not 0:
                score /= gettee.evaluationsAsEvaluatee.all().couunt()
            if Mission.objects.get(id=mission_ID).members.filter(id=getter_ID).count() is not 0 \
                    or Mission.objects.get(id=mission_ID).publisher.id is getter_ID:
                tele = gettee.tele
                data = {'missionStatus': "tele can be seen", 'tele': tele, 'averageScore': score}
                response = Response(json.dumps(data), 200)
            else:
                data = {'missionStatus': "tele can not be seen", 'averageScore': score}
                response = Response(json.dumps(data), 200)
        return response


class DealEvaluations(APIView):  # 获取用户做过的所有评价
    @staticmethod
    def get(request, user_ID):
        uid = checkUID(request.data)
        if uid is 0:
            response = Response("Unauthorized", 401)
        elif User.objects.filter(id=user_ID).count() == 0:
            response = Response("user Not Found", 404)
        else:
            user = User.objects.get(id=user_ID)
            evaluations = []
            for evaluation in user.evaluationsAsEvaluater.all():
                evaluations.append(evaluation.id)
            response = Response(evaluations, 200)
        return response


class DealEvaluation(APIView):  # 根据evaluation_ID获取评价
    @staticmethod
    def get(request, evaluation_ID):
        uid = checkUID(request.data)
        if uid is 0:
            response = Response("Unauthorized", 401)
        elif Evaluation.objects.filter(id=evaluation_ID).count() == 0:
            response = Response("evaluation Not Found", 404)
        else:
            evaluation = Evaluation.objects.get(id=evaluation_ID)
            timeStamp = evaluation.timeStamp.strftime("%Y-%m-%d %H:%M:%S")
            evaluateeID = evaluation.evaluatee.id
            evaluaterID = evaluation.evaluater.id
            missionID = evaluation.mission.id
            evaluationScore = evaluation.evaluationScore
            data = {'timeStamp': timeStamp, 'evaluateeID': evaluaterID, 'evaluaterID': evaluateeID,
                    'missionID':missionID, 'evaluationScore': evaluationScore}
            response = Response(json.dumps(data), 200)
        return response


class DealInfo(APIView):  # 修改个人信息
    @staticmethod
    def put(request, user_ID):  # request body:user
        uid = checkUID(request.data)
        if uid is 0:
            response = Response("Unauthorized", 401)
        elif User.objects.filter(id=user_ID).count() == 0:
            response = Response("User Not Found", 404)
        else:
            user = User.objects.get(id=user_ID)
            user.username = request.data.get('username')
            user.tele = request.data.get('tele')
            response = Response("OK", 200)
        return response


class DealCode(APIView):  # 修改密码
    @staticmethod
    def put(request, user_ID):  # request body:password
        uid = checkUID(request.data)
        if uid is 0:
            response = Response("Unauthorized", 401)
        elif User.objects.filter(id=user_ID).count() == 0:
            response = Response("User Not Found", 404)
        else:
            user = User.objects.get(id=user_ID)
            user.passwordAfterMD5 = passwordToMD5(RSAdecypt(request.get('passwordAfterRSA')))
            response = Response("OK", 200)
        return response


class DealEvaluate(APIView):  # 评分
    @staticmethod
    def post(request, evaluater_ID, mission_ID, evaluatee_ID):  # evaluater是评分人，evaluatee是被评分人
        uid = checkUID(request.data)
        if uid is 0:
            response = Response("Unauthorized", 401)
        elif User.objects.filter(id=evaluater_ID).count() == 0:
            response = Response("evaluater Not Found", 404)
        elif Mission.objects.filter(id=mission_ID).count() == 0:
            response = Response("mission Not Found", 404)
        elif User.objects.filter(id=evaluatee_ID).count() == 0:
            response = Response("evaluatee Not Found", 404)
        else:
            #  judge whether this evaluation has been made once
            mission = Mission.objects.get(id=mission_ID)
            evaluater = User.objects.get(id=evaluater_ID)
            evaluatee = User.objects.get(id=evaluatee_ID)
            if evaluatee.evaluationsAsEvaluatee.filter(evaluater=evaluater).count() != 0 and\
                evaluatee.evaluationsAsEvaluatee.filter(mission=mission).count() != 0:
                response = Response("Forbidden", 403)
            else:
                Evaluation.objects.create(evaluationScore=request.data.get('score'), timeStamp=datetime.datetime.now(),
                                          evaluater=evaluater, evaluatee=evaluatee, mission=mission)
                response = Response("OK", 200)
        return response


class DealCaptcha(APIView):  # 获取验证码
    @staticmethod
    def post(request):  # request body:user(regiter information only)
        return Response(['This is a POST of user/captcha', request.data])


class DealRegister(APIView):  # 注册
    @staticmethod
    def post(request):  # request body:user(regiter information only)
        return Response(['This is a POST of user/register', request.data])


class DealLogin(APIView):  # 登录
    @staticmethod
    def post(request):  # request body:user(login information only)
        return Response(['This is a POST of user/login', request.data])
