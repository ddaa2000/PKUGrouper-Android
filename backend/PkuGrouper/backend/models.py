from django.db import models

# Create your models here.


def fixtime(s):
    x = s.find('.')
    if x == -1:
        return s
    return s[:x]


class User(models.Model):
    mailbox = models.EmailField()
    username = models.CharField(max_length=200)
    tele = models.CharField(max_length=200)
    passwordAfterMD5 = models.CharField(max_length=200)

    def __str__(self):
        return str(self.id)


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
        return str(self.id)

    def showPublishTime(self):
        return fixtime(str(self.publishTime))


class Membership(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    mission = models.ForeignKey(Mission, on_delete=models.CASCADE)


class Applicantship(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    mission = models.ForeignKey(Mission, on_delete=models.CASCADE)


class Channel(models.Model):
    missions = models.ManyToManyField(Mission, through='MissionChannelship',
                                      related_name='channels')
    content = models.CharField(max_length=200)

    def __str__(self):
        return self.content


class MissionChannelship(models.Model):
    mission = models.ForeignKey(Mission, on_delete=models.CASCADE)
    channel = models.ForeignKey(Channel, on_delete=models.CASCADE)


class Evaluation(models.Model):
    evaluationScore = models.FloatField()
    timeStamp = models.DateTimeField(auto_now_add=True)
    evaluater = models.ForeignKey(User, on_delete=models.CASCADE,
                                  related_name='evaluationsAsEvaluater')
    evaluatee = models.ForeignKey(User, on_delete=models.CASCADE,
                                  related_name='evaluationsAsEvaluatee')
    mission = models.ForeignKey(Mission, on_delete=models.CASCADE,
                                related_name='evaluationsOfTheMission')

    def showTimeStamp(self):
        return fixtime(str(self.timeStamp))


class Message(models.Model):
    messageContent = models.CharField(max_length=1000)
    messageType = models.CharField(max_length=200)
    timeStamp = models.DateTimeField(auto_now_add=True)
    publisher = models.ForeignKey(User, on_delete=models.CASCADE,
                                  related_name='messagesAsPublisher')
    receivers = models.ManyToManyField(User, through='Receivership',
                                       related_name='messagesAsReciever')
    # 被举报人
    reportees = models.ManyToManyField(User, through='Reporteeship',
                                      related_name='messagesAsReportee')

    def __str__(self):
        return str(self.id)

    def showTimeStamp(self):
        return fixtime(str(self.timeStamp))


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
