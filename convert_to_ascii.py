import os
from PIL import Image

# ASCII字符集（从暗到亮排列）
ASCII_CHARS = '@%#*+=-:. '

def image_to_ascii(image_path, width=80, height=60):
    # 打开图片并转换为灰度
    img = Image.open(image_path).convert('L')
    # 调整尺寸（确保和ffmpeg的scale参数一致）
    img = img.resize((width, height))
    pixels = img.getdata()
    # 将像素映射到ASCII字符
    ascii_str = ''
    for i, pixel in enumerate(pixels):
        if i % width == 0 and i != 0:
            ascii_str += '\n'
        # 根据灰度值选择字符
        index = min(int(pixel / 255 * (len(ASCII_CHARS) - 1)), len(ASCII_CHARS) - 1)
        ascii_str += ASCII_CHARS[index]
    return ascii_str

# 主程序
if __name__ == '__main__':
    output_file = 'ascii_frames.txt'
    frames_dir = 'frames'

    # 按文件名排序（确保帧顺序正确）
    frame_files = sorted(os.listdir(frames_dir), key=lambda x: int(x.split('_')[1].split('.')[0]))

    with open(output_file, 'w', encoding='utf-8') as f:
        for frame in frame_files:
            if frame.endswith('.png'):
                frame_path = os.path.join(frames_dir, frame)
                ascii_frame = image_to_ascii(frame_path)
                f.write(ascii_frame + '\n===FRAME===\n')
                print(f'Processed: {frame}')

    print("Conversion complete, check file ascii_frames.txt")