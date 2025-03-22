@echo off
ffmpeg -i badapple.mp4 -vf "fps=30,scale=80:60" frames/frame_%%04d.png
pause