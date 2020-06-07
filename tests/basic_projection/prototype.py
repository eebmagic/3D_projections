import turtle

def project(point, angle):
    x, y, z = point

    x = x * (angle / (angle + z))
    y = y * (angle / (angle + z))
    
    return (x, y)


scale = 50
coords = [[1, 1, 1],
            [2, 2, 2],
            [1, 2, 2],
            [1, 2, 1],
            [1, 1, 2],
            [2, 1, 1],
            [2, 2, 1],
            [2, 1, 2]]

for ind, point in enumerate(coords):
    coords[ind] = [x * scale for x in point]


t = turtle.Turtle()
t.speed("fastest")
t.hideturtle()

for angle in range(1, 10):
    t.clear()

    print(f"h angle: {scale * angle}\ni: {angle}")
    for point in coords:
        t.penup()
        # t.goto(point[0]*scale, point[1]*scale)
        t.goto(project(point, scale*angle))
        t.pendown()
        t.circle(5)

    input("press enter for next angle")
print(done)
turtle.done()