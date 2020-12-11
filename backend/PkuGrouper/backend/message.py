from rest_framework.views import APIView
from rest_framework.response import Response

from .models import *
from django.utils import timezone
from .someFuncs import *
import random

def to_json(obj):
    mp = {
    "timeStamp" : str(obj.timeStamp)[:-7],
    "publisherID" : obj.publisher.id,
    "type" : obj.messageType,
    "messageContent" : eval(obj.messageContent),
    }
    if obj.messageType == 'Report':
        mp["reportee"] = obj.reportees.all()[0].id
    return mp

class DealMessages(APIView):#获取message
    #（这个应该是给出user_ID，返回一个此user未查看的message的数组）
    @staticmethod
    def get(request, user_ID):
        #return Response('This is a GET of messages/{user_ID}')
        try:
            who = User.objects.get(id=user_ID)
        except User.DoesNotExist:
            return Response("user Not Found",status=404)
        user_message = list(map(lambda p: to_json(p),
                            who.messagesAsReciever.all().order_by('-timeStamp')[:50])
                            )

        return Response(user_message)

class DealMessage(APIView):#根据message_ID获取信息
    @staticmethod
    def get(request, message_ID):
        try:
            who = Message.objects.get(id=message_ID)
        except Message.DoesNotExist:
            return Response("message Not Found",status=404)
        return Response(to_json(who))

class DealBug(APIView):#报告bug
    @staticmethod
    def post(request, user_ID):#request body:bug
        #return Response(['This is a POST of message/bug/{user_ID}', request.data])
        try:
            who = User.objects.get(id=user_ID)
        except User.DoesNotExist:
            return Response("user Not Found",status=404)
        m = Message(messageContent=request.body, messageType='Bug',
            publisher = who,
            )
        m.save()
        m.recievers.add(User.objects.get(id=1))
        return Response("OK")

        

class DealReport(APIView):#举报
    @staticmethod
    def post(request, user_ID, reportee_ID):#request body:report
        #return Response(['This is a POST of message/report/{user_ID}/{reportee_ID}', request.data])
        try:
            who = User.objects.get(id=user_ID)
        except User.DoesNotExist:
            return Response("user Not Found",status=404)
        try:
            other = User.objects.get(id=reportee_ID)
        except User.DoesNotExist:
            return Response("reportee Not Found",status=404)

        m = Message(messageContent=request.body, messageType='Report',
            publisher = who,
            )
        m.save()
        m.recievers.add(User.objects.get(id=1))
        m.reportees.add(other)

        return Response("OK")

class debug(APIView):#for debug
    @staticmethod
    def get(request):
        #users
        User.objects.all().delete()
        for i in range(100):
            q = User(mailbox=str(i)+'@pku.edu.cn',username='user_'+str(i),
                    tele = str(i),passwordAfterMD5='233')
            q.id = i+1
            q.save()
        user_list = User.objects.all()
        #admin

        admin = User.objects.get(id=1)
        #message
        Message.objects.all().delete()
        cnt = 0
        #bug
        for i in range(100):
            m = Message(messageContent=str(i),messageType='Bug',
                        publisher = user_list[random.randint(1,len(user_list)-1)],
                        )
            m.id = cnt+1
            cnt+=1
            m.save()
            m.recievers.add(admin)
        #report
        for i in range(100):
            m = Message(messageContent=str(i),messageType='Report',
                        publisher = user_list[random.randint(1,len(user_list)-1)],
                        )
            m.id = cnt+1
            cnt+=1
            m.save()
            m.recievers.add(admin)
            for j in range(1):
                m.reportees.add(user_list[random.randint(1,len(user_list)-1)])
        #Notice
        m = Message(messageContent=str(i),messageType='Notice',
                        publisher = admin,
                    )
        m.id = cnt+1
        cnt+=1
        m.save()
        for i in User.objects.all():
            m.recievers.add(i)

        return Response([len(User.objects.all()),len(Message.objects.all())])
class debug_clear(APIView):#for debug
    @staticmethod
    def get(request):
        User.objects.all().delete()
        Message.objects.all().delete()
