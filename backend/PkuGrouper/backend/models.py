from django.db import models

# Create your models here.

class User(models.Model):
    mailbox = models.EmailField()
    password = models.CharField(max_length=200)
    def __str__(self):
        return self.mailbox

class Mission(models.Model):
    title = models.CharField(max_length=200)
    content = models.CharField(max_length=1000)
    publishTime = models.DateTimeField(auto_now_add=True)
    applicationEndTime = models.DateTimeField()
    executionStartTime = models.DateTimeField()
    executionEndTime = models.DateTimeField()
    publisher = models.ForeignKey(User, on_delete=models.CASCADE,
                                  related_name='missionsAsPublisher')
    members = models.ManyToManyField(User, through='Membership',
                                     related_name='missionsAsMember')
    applicants = models.ManyToManyField(User, through='Applicantship',
                                        related_name='missionsAsApplicant')
    def __str__(self):
        return self.title

class Membership(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    mission = models.ForeignKey(Mission, on_delete=models.CASCADE)

class Applicantship(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    mission = models.ForeignKey(Mission, on_delete=models.CASCADE)

class Tag(models.Model):
    users = models.ManyToManyField(User, through='UserTagship',
                                   related_name='tags')
    missions = models.ManyToManyField(Mission, through='MissionTagship',
                                      related_name='tags')
    content = models.CharField(max_length=200)
    def __str__(self):
        return self.content

class UserTagship(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    tag = models.ForeignKey(Tag, on_delete=models.CASCADE)

class MissionTagship(models.Model):
    mission = models.ForeignKey(Mission, on_delete=models.CASCADE)
    tag = models.ForeignKey(Tag, on_delete=models.CASCADE)

class Evaluation(models.Model):
    evaluationScore = models.FloatField()
    timeStamp = models.DateTimeField(auto_now_add=True)
    evaluater = models.ForeignKey(User, on_delete=models.CASCADE,
                                  related_name='evaluationsAsEvaluater')
    evaluatee = models.ForeignKey(User, on_delete=models.CASCADE,
                                  related_name='evaluationsAsEvaluatee')
    mission = models.ForeignKey(Mission, on_delete=models.CASCADE,
                                related_name='evaluationsOfTheMission')

class Message(models.Model):
    messageContent = models.CharField(max_length=1000)
    messageType = models.CharField(max_length=200)
    timeStamp = models.DateTimeField(auto_now_add=True)
    publisher = models.ForeignKey(User, on_delete=models.CASCADE,
                                  related_name='messagesAsPublisher')
    recievers = models.ManyToManyField(User, through='Receivership',
                                       related_name='messagesAsReciever')
    #被举报人
    reportees = models.ManyToManyField(User, through='Reporteeship',
                                      related_name='messagesAsReportee')
    def __str__(self):
        return self.messageContent

class Receivership(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    message = models.ForeignKey(Message, on_delete=models.CASCADE)

class Reporteeship(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    message = models.ForeignKey(Message, on_delete=models.CASCADE)

class Captcha(models.Model):
    timeStamp = models.DateTimeField(auto_now=True)
    mailbox = models.EmailField()
    captchaContent = models.CharField(max_length=200)
