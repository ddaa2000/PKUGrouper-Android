### PkuGrouper Backend

The project is constructed by spd, zsn, jzh and hzq,



and maintenanced by spd and zsn.

------

The README file is only a simple manual for deploying the server on Linux.



Before deploying the server, please use `pip3 install djangorestframework` and `pip3 install rsa` to install required packages.

------

When you want to deploy the server, please use `nohup python3 -u manage.py runserver 0:8000 > backend.log 2>&1 &` to run the server in the background.



The logs are in `backend.log`.



When you want to kill the server, please use `ps -A` to find the PID of `python3` (may have two, you can choose any one of them).



Then use `kill PID` to kill the server.

------

If you want to use the server formally, please find the 26th line in `PkuGrouper/settings.py` which writes `DEBUG = True`.



The replace it with `DEBUG = False`.