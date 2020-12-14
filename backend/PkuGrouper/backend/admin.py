from django.contrib import admin

# Register your models here.

from .models import *

admin.site.register(User)
admin.site.register(Mission)
admin.site.register(Membership)
admin.site.register(Applicantship)
admin.site.register(Channel)
admin.site.register(MissionChannelship)
admin.site.register(Evaluation)
admin.site.register(Message)
admin.site.register(Receivership)
admin.site.register(Reporteeship)
admin.site.register(Captcha)
