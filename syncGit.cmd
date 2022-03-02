REM https://stackoverflow.com/questions/203090/how-do-i-get-current-datetime-on-the-windows-command-line-in-a-suitable-format
FOR /F "Tokens=1-3 Delims=/ " %%A In ('RoboCopy/NJH /L "\|" Null'
) Do Set "mydate=%%A_%%B_%%C" & GoTo :Break
:Break
Set "mydate=%mydate:~2%"
git add -A
git commit -a -m "Commit %mydate%"
git pull
git push
Timeout -1
