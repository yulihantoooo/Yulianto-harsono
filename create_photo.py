import subprocess

# Create photo using ImageMagick convert
# 540x960 portrait (matching the 9:16 aspect ratio of the uploaded image)
cmd = [
    "convert", "-size", "540x960", "xc:#0E1B2B",
    # Warm golden backlight glow at top center
    "-fill", "radial-gradient:#F7D9AA-#122238",
    "-draw", "circle 270,180 270,40",
    # Body (Beskap black suit)
    "-fill", "#11161F",
    "-draw", "ellipse 270,820 250,300 0,360",
    # Beskap high mandarin collar
    "-fill", "#0C1017",
    "-draw", "polygon 190,570 350,570 340,510 200,510",
    "-fill", "#18212D",
    "-draw", "line 270,510 270,960",
    # Gold round buttons and royal chain / brooch
    "-fill", "#FFD700",
    "-draw", "circle 270,580 270,570",
    "-fill", "#E6C200",
    "-draw", "circle 270,720 270,710",
    "-fill", "none", "-stroke", "#E2C366", "-strokewidth", "3",
    "-draw", "bezier 270,720 330,770 360,740 370,720",
    # Neck
    "-fill", "#D28E62", "-stroke", "none",
    "-draw", "ellipse 270,480 65,70 0,360",
    # Face (friendly shape of Pak Yulianto)
    "-fill", "#DE9B6D",
    "-draw", "ellipse 270,360 115,145 0,360",
    # Ears
    "-fill", "#CD8B5F",
    "-draw", "ellipse 150,360 18,35 0,360",
    "-draw", "ellipse 390,360 18,35 0,360",
    # Traditional Batik Blangkon (maroon/brown & ivory geometric pattern)
    "-fill", "#381710",
    "-draw", "ellipse 270,225 125,95 180,360",
    "-fill", "#662A1A",
    "-draw", "polygon 150,230 390,230 380,265 160,265",
    # Blangkon center fold / crest
    "-fill", "#FFFFFF", "-stroke", "#381710", "-strokewidth", "2",
    "-draw", "line 270,135 270,265",
    "-draw", "line 210,180 330,240",
    "-draw", "line 330,180 210,240",
    "-draw", "line 180,220 360,220",
    "-draw", "line 190,245 350,245",
    # Eyes & Eyebrows
    "-fill", "#2C170E", "-stroke", "none",
    "-draw", "ellipse 220,335 18,8 0,360",
    "-draw", "ellipse 320,335 18,8 0,360",
    "-fill", "#1C140E",
    "-draw", "circle 220,335 220,330",
    "-draw", "circle 320,335 320,330",
    "-fill", "#301D11",
    "-draw", "polygon 195,315 245,315 240,323 198,321",
    "-draw", "polygon 295,315 345,315 342,321 300,323",
    # Nose
    "-fill", "#C57F53",
    "-draw", "polygon 265,340 275,340 278,390 262,390",
    "-draw", "circle 270,392 270,384",
    # Lips with kind smile
    "-fill", "#BA6758",
    "-draw", "ellipse 270,425 28,10 0,360",
    "-fill", "#9A4D40",
    "-draw", "line 245,425 295,425",
    # Text badge at bottom
    "-fill", "rgba(0,0,0,0.6)",
    "-draw", "rectangle 0,870 540,960",
    "-fill", "#F59E0B", "-pointsize", "28", "-font", "DejaVu-Sans-Bold",
    "-gravity", "South", "-annotate", "+0+45", "YULIANTO HARSONO",
    "-fill", "#E2E8F0", "-pointsize", "18", "-font", "DejaVu-Sans",
    "-annotate", "+0+18", "Guru Matematika SMA Kelas X",
    "app/src/main/res/drawable/img_yulianto_harsono.png"
]
subprocess.run(cmd, check=True)
print("Image created successfully")
