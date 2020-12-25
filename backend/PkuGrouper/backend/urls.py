from django.urls import path

from . import views, user, mission, message, finds

urlpatterns = [
    path('', views.index, name='index'),

    #path for user
    path('user/self/<int:user_ID>/', user.DealSelf.as_view(), name='user/self/'),
    path('user/member/<int:getter_ID>/<int:mission_ID>/<int:gettee_ID>/', user.DealMember.as_view(), name='user/member/'),
    path('user/evaluations/<int:user_ID>/', user.DealEvaluations.as_view(), name='user/evaluations/'),
    path('user/evaluation/<int:evaluation_ID>/', user.DealEvaluation.as_view(), name='user/evaluation/'),
    path('user/info/<int:user_ID>/', user.DealInfo.as_view(), name='user/info/'),
    path('user/code/<int:user_ID>/', user.DealCode.as_view(), name='user/code/'),
    path('user/tags/<int:user_ID>/', user.DealTags.as_view(), name='user/tags/'),
    path('user/evaluate/<int:evaluater_ID>/<int:mission_ID>/<int:evaluatee_ID>/', user.DealEvaluate.as_view(), name='user/evaluate/'),
    path('user/captcha/', user.DealCaptcha.as_view(), name='user/captcha/'),
    path('user/register/', user.DealRegister.as_view(), name='user/register/'),
    path('user/login/', user.DealLogin.as_view(), name='user/login/'),
    path('user/fixpasswordcaptcha/', user.DealFixPasswordCaptcha.as_view(), name='user/fixpasswordcaptcha/'),
    path('user/fixpassword/', user.DealFixPassword.as_view(), name='user/fixpassword/'),

    #path for mission
    path('mission/<int:user_ID>/<int:mission_ID>/', mission.DealMission.as_view(), name='mission/'),
    path('missions/<int:user_ID>/', mission.DealMissions.as_view(), name='missions/'),
    path('mission/create/<int:user_ID>/', mission.DealCreate.as_view(), name='mission/create/'),
    path('mission/delete/<int:user_ID>/<int:mission_ID>/', mission.DealDelete.as_view(), name='mission/delete/'),
    path('mission/join/<int:user_ID>/<int:mission_ID>/', mission.DealJoin.as_view(), name='mission/join/'),
    path('mission/accept/<int:user_ID>/<int:mission_ID>/<int:applicant_ID>/', mission.DealAccept.as_view(), name='mission/accept/'),
    path('mission/reject/<int:user_ID>/<int:mission_ID>/<int:applicant_ID>/', mission.DealReject.as_view(), name='mission/reject/'),
    path('mission/fire/<int:user_ID>/<int:mission_ID>/<int:member_ID>/', mission.DealFire.as_view(), name='mission/fire/'),
    path('mission/start/<int:user_ID>/<mission_ID>/', mission.DealStart.as_view(), name='mission/start/'),
    path('mission/finish/<int:user_ID>/<mission_ID>/', mission.DealFinish.as_view(), name='mission/Finish/'),
    path('mission/quit/<int:user_ID>/<mission_ID>/', mission.DealQuit.as_view(), name='mission/Quit/'),

    #path for message
    path('messages/<int:user_ID>/', message.DealMessages.as_view(), name='messages/'),
    path('message/<int:message_ID>/', message.DealMessage.as_view(), name='message/'),
    path('message/bug/<int:user_ID>/', message.DealBug.as_view(), name='message/bug/'),
    path('message/report/<int:user_ID>/<int:reportee_ID>/', message.DealReport.as_view(), name='message/report/'),

    #path for finds
    path('findmissions/<int:user_ID>/', finds.DealFindMissions.as_view(), name='/findmissions/'),
    path('findmessages/<int:user_ID>/', finds.DealFindMessages.as_view(), name='/findmessages/'),
    path('findevaluations/<int:user_ID>/', finds.DealFindEvaluations.as_view(), name='/findevaluations/'),
    path('findusers/<int:getter_ID>/<int:mission_ID>/', finds.DealFindUsers.as_view(), name='/findusers/'),

    #下面是上面的path去掉末尾'/'的复制
    
    #path for user
    path('user/self/<int:user_ID>', user.DealSelf.as_view(), name='user/self'),
    path('user/member/<int:getter_ID>/<int:mission_ID>/<int:gettee_ID>', user.DealMember.as_view(), name='user/member'),
    path('user/evaluations/<int:user_ID>', user.DealEvaluations.as_view(), name='user/evaluations'),
    path('user/evaluation/<int:evaluation_ID>', user.DealEvaluation.as_view(), name='user/evaluation'),
    path('user/info/<int:user_ID>', user.DealInfo.as_view(), name='user/info'),
    path('user/code/<int:user_ID>', user.DealCode.as_view(), name='user/code'),
    path('user/tags/<int:user_ID>', user.DealTags.as_view(), name='user/tags'),
    path('user/evaluate/<int:evaluater_ID>/<int:mission_ID>/<int:evaluatee_ID>', user.DealEvaluate.as_view(), name='user/evaluate'),
    path('user/captcha', user.DealCaptcha.as_view(), name='user/captcha'),
    path('user/register', user.DealRegister.as_view(), name='user/register'),
    path('user/login', user.DealLogin.as_view(), name='user/login'),
    path('user/fixpasswordcaptcha', user.DealFixPasswordCaptcha.as_view(), name='user/fixpasswordcaptcha'),
    path('user/fixpassword', user.DealFixPassword.as_view(), name='user/fixpassword'),

    #path for mission
    path('mission/<int:user_ID>/<int:mission_ID>', mission.DealMission.as_view(), name='mission'),
    path('missions/<int:user_ID>', mission.DealMissions.as_view(), name='missions'),
    path('mission/create/<int:user_ID>', mission.DealCreate.as_view(), name='mission/create'),
    path('mission/delete/<int:user_ID>/<int:mission_ID>', mission.DealDelete.as_view(), name='mission/delete'),
    path('mission/join/<int:user_ID>/<int:mission_ID>', mission.DealJoin.as_view(), name='mission/join'),
    path('mission/accept/<int:user_ID>/<int:mission_ID>/<int:applicant_ID>', mission.DealAccept.as_view(), name='mission/accept'),
    path('mission/reject/<int:user_ID>/<int:mission_ID>/<int:applicant_ID>', mission.DealReject.as_view(), name='mission/reject'),
    path('mission/fire/<int:user_ID>/<int:mission_ID>/<int:member_ID>', mission.DealFire.as_view(), name='mission/fire'),
    path('mission/start/<int:user_ID>/<mission_ID>', mission.DealStart.as_view(), name='mission/start'),
    path('mission/finish/<int:user_ID>/<mission_ID>', mission.DealFinish.as_view(), name='mission/Finish'),
    path('mission/quit/<int:user_ID>/<mission_ID>', mission.DealQuit.as_view(), name='mission/Quit'),

    #path for message
    path('messages/<int:user_ID>', message.DealMessages.as_view(), name='messages'),
    path('message/<int:message_ID>', message.DealMessage.as_view(), name='message'),
    path('message/bug/<int:user_ID>', message.DealBug.as_view(), name='message/bug'),
    path('message/report/<int:user_ID>/<int:reportee_ID>', message.DealReport.as_view(), name='message/report'),

    #path for finds
    path('findmissions/<int:user_ID>', finds.DealFindMissions.as_view(), name='/findmissions'),
    path('findmessages/<int:user_ID>', finds.DealFindMessages.as_view(), name='/findmessages'),
    path('findevaluations/<int:user_ID>', finds.DealFindEvaluations.as_view(), name='/findevaluations'),
    path('findusers/<int:getter_ID>/<int:mission_ID>', finds.DealFindUsers.as_view(), name='/findusers'),
]
