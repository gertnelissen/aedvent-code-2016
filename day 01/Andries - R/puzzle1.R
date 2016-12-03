library("data.table")

input <- "R5, L2, L1, R1, R3, R3, L3, R3, R4, L2, R4, L4, R4, R3, L2, L1, L1, R2, R4, R4, L4, R3, L2, R1, L4, R1, R3, L5, L4, L5, R3, L3, L1, L1, R4, R2, R2, L1, L4, R191, R5, L2, R46, R3, L1, R74, L2, R2, R187, R3, R4, R1, L4, L4, L2, R4, L5, R4, R3, L2, L1, R3, R3, R3, R1, R1, L4, R4, R1, R5, R2, R1, R3, L4, L2, L2, R1, L3, R1, R3, L5, L3, R5, R3, R4, L1, R3, R2, R1, R2, L4, L1, L1, R3, L3, R4, L2, L4, L5, L5, L4, R2, R5, L4, R4, L2, R3, L4, L3, L5, R5, L4, L2, R3, R5, R5, L1, L4, R3, L1, R2, L5, L1, R4, L1, R5, R1, L4, L4, L4, R4, R3, L5, R1, L3, R4, R3, L2, L1, R1, R2, R2, R2, L1, L1, L2, L5, L3, L1"
clean <- strsplit(input, ", ")[[1]]
dt <- data.table(data=clean)
dt[,direction:=substr(data, 1, 1)]
dt[,distance:=substr(data, 2, nchar(data))]


changewind <- function(dir, prev_wind="N") {
  wind <- c("N", "E", "S", "W")
  v <- ifelse(dir == "R", 
         wind[(match(prev_wind, wind) %% 4) + 1], 
         wind[ifelse(match(prev_wind, wind) == 1, 4, match(prev_wind, wind)-1)]
  )
  return(v)
}


mylist <- c("N")
for (ind in 1:length(dt$direction)) {
  dir <- dt$direction[ind]
  w <- changewind(dir, mylist[ind])
  mylist <- c(mylist, w)
}
ll <- tail(mylist, n=length(mylist)-1)
dt[,wind:=ll]

dt[,N:=ifelse(wind=="N",distance,0)]
dt[,E:=ifelse(wind=="E",distance,0)]
dt[,S:=ifelse(wind=="S",distance,0)]
dt[,W:=ifelse(wind=="W",distance,0)]

dist_N <- sum(as.integer(dt$N))
dist_E <- sum(as.integer(dt$S))
dist_S <- sum(as.integer(dt$E))
dist_W <- sum(as.integer(dt$W))

vertical <- abs(dist_N - dist_S)
horizontal <- abs(dist_E - dist_W)


# second puzzle

X <- c(0)
Y <- c(0)
for (ind in 1:length(dt$direction)) {
  n <- as.integer(dt$N[ind])
  e <- as.integer(dt$E[ind])
  s <- as.integer(dt$S[ind])
  w <- as.integer(dt$W[ind])
  #newX <- X[ind] + n - s
  #newY <- Y[ind] + e - w
  if (n != 0) {
    for (i in 1:n) {
      newX <- X[length(X)] 
      newY <- Y[length(Y)] + 1
      X <- c(X, newX)
      Y <- c(Y, newY)
    }
  } else if (s != 0) {
    for (i in 1:s) {
      newX <- X[length(X)] 
      newY <- Y[length(Y)] - 1
      X <- c(X, newX)
      Y <- c(Y, newY)
    }
  } else if (e != 0) {
    for (i in 1:e) {
      newX <- X[length(X)]  + 1
      newY <- Y[length(Y)] 
      X <- c(X, newX)
      Y <- c(Y, newY)
    }
  } else {
    for (i in 1:w) {
      newX <- X[length(X)] -1
      newY <- Y[length(Y)] 
      X <- c(X, newX)
      Y <- c(Y, newY)
    }
  }
  
}
X <- tail(X, n=length(X)-1)
Y <- tail(Y, n=length(Y)-1)

dt2 <- data.table(X=X, Y=Y)
dt2[,concat:=paste0(as.character(X),as.character(Y))]
dt2[duplicated(dt2$concat)][1] # X + Y

