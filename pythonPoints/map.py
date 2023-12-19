points = []
square = []

A = [45.786191, 4.861050]
B = [45.785836, 4.903327]
C = [45.772856, 4.859939]
D = [45.772223, 4.901491]

points.append(A)
points.append(B)
points.append(C)
points.append(D)

def midPoint (pointA, pointB, pointList):
  # Points entre A et B:
  # distance AB : 
  ABdist = [pointB[0]-pointA[0],pointB[1]-pointA[1]]
  ABstep = [ABdist[0]/9,ABdist[1]/9]
  for i in range(8):
    pointList.append([pointA[0]+ABstep[0]*(i+1), pointA[1]+ABstep[1]*(i+1)])

# On fait le point manquant entre les deux extrémités:

AC = [(A[0]+C[0])/2, (A[1]+C[1])/2]
BD = [(B[0]+D[0])/2, (B[1]+D[1])/2]

points.append(AC)
points.append(BD)



midPoint(A,B,points)
midPoint(AC, BD, points)
midPoint(C,D, points)


E = [45.770707,4.876600]
F = [45.767617,4.917500]
G = [45.756226,4.876393]
H = [45.749442,4.916804]

EG = [(E[0]+G[0])/2,(E[1]+G[1])/2]
FH = [(F[0]+H[0])/2,(F[1]+H[1])/2]

points.append(E)
points.append(F)
points.append(G)
points.append(H)
points.append(EG)
points.append(FH)

midPoint(E,F,points)
midPoint(EG, FH, points)
midPoint(G,H, points)

for i in range(len(points)):
  print(points[i])