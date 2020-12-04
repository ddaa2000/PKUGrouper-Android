import smtplib
import email
from email.mime.text import MIMEText
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart

host = 'smtp.163.com'
port = '465'
sender = 'PkuGrouper@163.com'
password = 'XYVYGAZTACOJZKGW'
receiver = '@999.com'
body = 'This is a homework'
subject = 'Homework'

message = MIMEText(body, 'plain', 'utf-8')
message['From'] = 'PkuGrouper<PkuGrouper@163.com>'
message['To'] = '@999.com'
message['Subject'] = subject
email_client = smtplib.SMTP_SSL(host, port)
login_result = email_client.login(sender, password)
email_client.sendmail(from_addr=sender,
                         to_addrs=receiver, msg=message.as_string())
email_client.close()
