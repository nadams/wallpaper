@ECHO OFF
IF NOT "%~f0" == "~f0" GOTO :WinNT
@"c:/Lang/ruby/bin/ruby.exe" "c:/Lang/ruby/bin/compass" %1 %2 %3 %4 %5 %6 %7 %8 %9
GOTO :EOF
:WinNT
@"c:/Lang/ruby/bin/ruby.exe" "%~dpn0" %*
