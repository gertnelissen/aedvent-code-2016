library(data.table)
library(stringr)

data <- read.table("input8.txt", fill = T)
data <- read.csv("input8.txt",header = F)
names(data) <- "inst"
inst <- as.character(data$inst)



parseline <- function(line, mat) {
  split <- str_split(line, pattern = " ")[[1]]
  if (split[[1]] == "rotate") {
    pos <- gsub(x=split[[3]], pattern = ".=(.)", replacement = "\\1")
    m <- parserotate(as.logical(split[[2]] == "row"), as.numeric(pos)+1, as.numeric(split[[5]]), mat)
  } else {
    x <- gsub("(.+)x(.+)$", x = split[[2]], replacement = "\\1")
    y <- gsub("(.+)x(.+)$", x = split[[2]], replacement = "\\2")
    m <- parserect(as.numeric(x), as.numeric(y), mat)
  }
  return(m)
}

parserotate <- function(is.row, pos, quantity, mat) {
  if (is.row) {
    last <- tail(mat[pos,], n = quantity)
    sh <- shift(mat[pos,], quantity)
    sh[1:quantity] <- last
    mat[pos,] <- sh
  } else {
    last <- tail(mat[,pos], n = quantity)
    sh <- shift(mat[,pos], quantity)
    sh[1:quantity] <- last
    mat[,pos] <- sh
  }
  return(mat)
}

parserect <- function(column, row, mat) {
  mat[1:row, 1:column] <- 1 
  return(mat)
}

# matrix
m <- matrix(rep(0,50*6), 6)

for (line in inst) {
  m <<- parseline(line, m)
}
print(sum(m)) # part1
print(m) # part2
