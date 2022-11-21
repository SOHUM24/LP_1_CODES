from tkinter import *

root = Tk()
root.geometry("1280x720")
root.maxsize(1920,1080)
root.minsize(1280,720)
root.title("Mera GUI")

######################################################################################
F1=Frame(root,bg='black')
F1.pack(side=TOP,fill=X)
L1=Label(F1,text="Hi , Welcome to my GUI !!",bg="black",fg="dark grey",pady=40,padx=40,font="comicsansms 30 bold")
L1.pack()


######################################################################################
F2=Frame(root,bg="grey")
F2.pack(side=BOTTOM,fill=X)
L2=Label(F2,text="Copy Right 2022-24" , bg="black",fg="dark grey",pady=30)
L2.pack()

F3=Frame(root,bg="Dark grey",pady=50)
F3.pack(side=TOP,fill=X)

L3 = Label(F3,bg="Dark grey")
L3.pack()

def Click1():
    L2.configure(text="You just Clicked !!")
Btn1=Button(F2,text="Click Here",bg="yellow",fg="red",command=Click1)
Btn1.pack()

F4=Frame(root,bg="light blue",pady=40)
F4.pack(side=TOP,fill=X)

L4 = Label(F4,bg="light blue")
L4.pack()


E1=Entry(F4,width=40)
E1.pack()

def click2():
    txt="You wrote : "+E1.get()
    L4.configure(text=txt)

Btn2=Button(F4,text="Enter", bg="cyan",fg="magenta",command=click2)
Btn2.pack()
root.mainloop()