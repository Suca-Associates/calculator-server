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
pip install -r requirements.txt
python manage.py runserver