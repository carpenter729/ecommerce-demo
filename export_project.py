import os

# 配置：需要包含的文件后缀
INCLUDED_EXTENSIONS = {'.java', '.xml', '.yml', '.yaml', '.properties', '.sql', '.gradle'}
# 配置：需要忽略的目录
IGNORED_DIRS = {'.git', '.idea', '.vscode', 'target', 'build', 'node_modules', 'logs'}

def is_text_file(filename):
    return any(filename.endswith(ext) for ext in INCLUDED_EXTENSIONS)

def print_directory_structure(root_dir):
    output = []
    output.append("# Project Structure")
    output.append("```")
    for root, dirs, files in os.walk(root_dir):
        # 过滤忽略的目录
        dirs[:] = [d for d in dirs if d not in IGNORED_DIRS]
        level = root.replace(root_dir, '').count(os.sep)
        indent = ' ' * 4 * level
        output.append(f"{indent}{os.path.basename(root)}/")
        subindent = ' ' * 4 * (level + 1)
        for f in files:
            if is_text_file(f):
                output.append(f"{subindent}{f}")
    output.append("```\n")
    return "\n".join(output)

def print_file_contents(root_dir):
    output = []
    output.append("# File Contents")
    for root, dirs, files in os.walk(root_dir):
        dirs[:] = [d for d in dirs if d not in IGNORED_DIRS]
        for file in files:
            if is_text_file(file):
                file_path = os.path.join(root, file)
                # 获取相对路径作为标题
                rel_path = os.path.relpath(file_path, root_dir)

                output.append(f"\n## File: {rel_path}")
                output.append(f"```{file.split('.')[-1]}")
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        output.append(f.read())
                except Exception as e:
                    output.append(f"Error reading file: {e}")
                output.append("```")
    return "\n".join(output)

if __name__ == "__main__":
    current_dir = os.getcwd() # 或者手动指定你的项目路径
    print(print_directory_structure(current_dir))
    print(print_file_contents(current_dir))