import rsa
from base64 import b64encode, b64decode
import hashlib
from .models import *
import smtplib
import email
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart

def RSAdecypt(passwordAfterRSA):
    private_key=rsa.PrivateKey.load_pkcs1(open('./backend/private_key.pem', 'rb').read())
    de_password = b64decode(passwordAfterRSA.encode('utf8'))
    password = rsa.decrypt(de_password,private_key).decode('utf8')
    return password

def passwordToMD5(password):
    return hashlib.md5(password.encode()).hexdigest()

def sendMail(mailbox, captchaContent):
    if mailbox.startswith('@'):
        return 0
    if not mailbox.endswith('@pku.edu.cn') and not mailbox.endswith('@stu.pku.edu.cn'):
        return 0
    host = 'smtp.163.com'
    port = '465'
    sender = 'PkuGrouper@163.com'
    password = 'XYVYGAZTACOJZKGW'
    receiver = mailbox
    body = '您的验证码是'+captchaContent+'，请在5分钟内完成注册！'
    subject = '【PkuGrouper】您的邮箱验证码'
    message = MIMEText(body, 'plain', 'utf-8')
    message['From'] = 'PkuGrouper<PkuGrouper@163.com>'
    message['To'] = mailbox+'<'+mailbox+'>'
    message['Subject'] = subject
    email_client = smtplib.SMTP_SSL(host, port)
    login_result = email_client.login(sender, password)
    email_client.sendmail(from_addr=sender,
                          to_addrs=receiver, msg=message.as_string())
    email_client.close()
    return 1

def checkUID(data):
    if type(data) != dict:
        return 0
    senderID = data.get('senderID')
    if senderID == None:
        return 0
    passwordAfterRSA = data.get('passwordAfterRSA')
    if passwordAfterRSA == None:
        return 0
    if type(senderID) != int:
        return 0
    if type(passwordAfterRSA) != str:
        return 0
    if User.objects.filter(id=senderID).count() == 0:
        return 0
    user = User.objects.get(id=senderID)
    if passwordToMD5(RSAdecypt(passwordAfterRSA)) != user.passwordAfterMD5:
        return 0
    return user.id
