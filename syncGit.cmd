REM https://stackoverflow.com/questions/203090/how-do-i-get-current-datetime-on-the-windows-command-line-in-a-suitable-format
For /f "tokens=1-3 delims=. " %%a in ('date /t') do (set mydate=%%c%%b%%a)
git add -A
git commit -a -m "Commit %mydate%"
git pull
git push