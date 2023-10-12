# calculator-server
server side of the Calculator App


Python environment

# Linux
sudo apt-get install python3-venv    # If needed
python3 -m venv .venv
source .venv/bin/activate

# macOS
python3 -m venv .venv
source .venv/bin/activate

# Windows
py -3 -m venv .venv
.venv\scripts\activate

# Environment selection
python -m pip install --upgrade pip
python -m pip install django

# Run
> pip install -r requirements.txt

If you are using SQLLite3 as database, please run the migrations
>python manage.py migrate

>python manage.py runserver