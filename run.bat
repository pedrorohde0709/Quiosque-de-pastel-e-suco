@echo off
REM ======================================================
REM Arquivo para COMPILAR e EXECUTAR o projeto JavaFX
REM (ajuste o caminho do FX_PATH se sua pasta for outra)
REM ======================================================

set FX_PATH=C:\Users\pedro\Downloads\openjfx-21.0.9_windows-x64_bin-sdk\javafx-sdk-21.0.9\lib

echo Compilando arquivos .java ...
javac --module-path "%FX_PATH%" --add-modules javafx.controls Main.java QuiosqueSistema.java Atendimento.java Item.java Pastel.java Suco.java
if errorlevel 1 (
  echo.
  echo Ocorreu um erro na compilacao. Verifique as mensagens acima.
  pause
  exit /b
)

echo.
echo Executando aplicacao ...
java --module-path "%FX_PATH%" --add-modules javafx.controls Main
echo.
pause
