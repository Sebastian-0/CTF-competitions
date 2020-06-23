
def cheeky(num):
    return num // 2

def frob(string, idx, length, depth):
    mean = (idx + length) // 2
    
    if idx < length - 1:
        #print((ord(string[mean]) - ord('0')) ^ 42, end='')
        print(string[mean], end='')
        print("/", end = '')
#        if depth == 3:
#            mean = 8

        frob(string, idx, mean, depth + 1)
        frob(string, mean+1, length, depth + 1)


flag = "your flag is in another castle"
frob(flag, 0, len(flag), 0)
print("0")
