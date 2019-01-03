import os
import sys

if len(sys.argv) < 2:
    print("please input a java file")
    exit()

java_file = sys.argv[1]

# compile java file
print("javac '%s'" % java_file)
rs = os.system("javac '%s'" % (java_file))

if rs!=0:
    print("java file compile error")
    exit()

package = os.path.dirname(java_file)

# get input data
inputs = []
ans = []
with open(java_file, 'r') as f:
    # skip non test data line
    for line in f:
        if line.strip() == '--test--':
            break

    s = ''
    is_ans = False
    for line in f:
        if line.strip() == '--test--':
            break
        elif not line.startswith('--'):
            s += line
        else:
            if not is_ans:
                inputs += [s.replace('\n', '\\n')]
            else:
                ans += [s.strip()]

            is_ans = not is_ans
            s = ""

    if s != '':
        if not is_ans:
            inputs += [s]
        else:
            ans += [s.strip()]

print("get %d test data" % (len(inputs)))

for i, (test, out) in enumerate(zip(inputs, ans)):
    print('-'*10+str(i+1).zfill(2)+"/"+str(len(inputs)).zfill(2)+'-'*10)
    command = "cd %s && echo '%s' | java Main " % (package,test)
    print("input :\n%s" % (test.replace("\\n", "\n").strip()))
    print("expect :\n%s" % (out))
    print("get :")
    # print(command)
    os.system(command)

print('-'*20)
