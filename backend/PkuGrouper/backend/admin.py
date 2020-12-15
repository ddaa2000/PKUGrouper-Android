from django.contrib import admin

# Register your models here.

from .models import *

class UserAdmin(admin.ModelAdmin):
    list_display = ('mailbox', 'id')

admin.site.register(User, UserAdmin)

class MissionAdmin(admin.ModelAdmin):
    list_display = ('title', 'id', 'showPublishTime')

admin.site.register(Mission, MissionAdmin)

class MembershipAdmin(admin.ModelAdmin):
    list_display = ('mission', 'user')
admin.site.register(Membership, MembershipAdmin)

class ApplicantshipAdmin(admin.ModelAdmin):
    list_display = ('mission', 'user')
    
admin.site.register(Applicantship, ApplicantshipAdmin)
admin.site.register(Channel)

class MissionChannelshipAdmin(admin.ModelAdmin):
    list_display = ('mission', 'channel')
admin.site.register(MissionChannelship, MissionChannelshipAdmin)

class EvaluationAdmin(admin.ModelAdmin):
    list_display = ('id',
                    'evaluater',
                    'evaluatee',
                    'showTimeStamp',
                    'evaluationScore')
admin.site.register(Evaluation, EvaluationAdmin)

class MessageAdmin(admin.ModelAdmin):
    list_display = ('id',
                    'messageType',
                    'messageContent',
                    'showTimeStamp',
                    'publisher')

admin.site.register(Message, MessageAdmin)

class ReceivershipAdmin(admin.ModelAdmin):
    list_display = ('message', 'user')

admin.site.register(Receivership, ReceivershipAdmin)

class ReporteeshipAdmin(admin.ModelAdmin):
    list_display = ('message', 'user')

admin.site.register(Reporteeship, ReporteeshipAdmin)

class CaptchaAdmin(admin.ModelAdmin):
    list_display = ('mailbox', 'timeStamp')

admin.site.register(Captcha, CaptchaAdmin)
