library("data.table")
data <- read.table("input3.txt")

dt <- data.table(data)
dt[,max:=max(V1, V2, V3), by=1:nrow(dt)]
dt[,max2:=max*2]
dt[,valid:=ifelse(max2-V1-V2-V3 >= 0,F,T)]
sum(dt$valid)

# second puzzle

data <- read.table("input3.txt")
all <- c(data[,1], data[,2], data[,3])
row1 <- all[seq(1,length(all),3)]
row2 <- all[seq(2,length(all),3)]
row3 <- all[seq(3,length(all),3)]

dt <- data.table(V1=row1, V2=row2, V3=row3)
dt[,max:=max(V1, V2, V3), by=1:nrow(dt)]
dt[,max2:=max*2]
dt[,valid:=ifelse(max2-V1-V2-V3 >= 0,F,T)]
sum(dt$valid)