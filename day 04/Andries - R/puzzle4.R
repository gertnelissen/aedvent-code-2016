library('data.table')
library('stringr')

data <- read.table("input4.txt")
names(data) <- "input"
dt <- data.table(data)
dt[,input:=as.character(dt$input)]

regex <- "(.*)-(\\d+)\\[(.*)\\]"
dt[,name:=gsub(regex, "\\1", input)]
dt[,sectorId:=gsub(regex, "\\2", input)]
dt[,checksum:=gsub(regex, "\\3", input)]

calcChecksum <- function(input) {
  occurences <- str_count(input, letters)
  return(maxList(occurences))
}

maxList <- function(input, number=5) {
  code <- c()
  
  res <- sort(input, index.return=T, decreasing = T)
  temp <- data.table(x=res$x, ix=res$ix)
  noc5 <- res$x[number]
  temp <- temp[temp[,x>=noc5]]
  
  occ <- sort(unique(temp$x), decreasing = T)
  for (i in 1:length(occ)) {
    sorted.i <- sort(temp[x==occ[i]]$ix)
    
    if(length(sorted.i) >= number || length(sorted.i) >= number-length(code)) {
      code <- c(code, head(sorted.i, n=number-length(code)))
      return(str_c(letters[code], collapse = ""))
    } else {
      code <- c(code, sorted.i)
    }
  }
}

dt[,calcChecksum:=calcChecksum(name), by=1:nrow(dt)]
sum(as.integer(dt[checksum==calcChecksum]$sectorId))

## part 2

dt[,splitted:=str_split(name, pattern = "-")]

runSeq <- function(ll, number) {
  return(str_c(letters[(ll + (number %% 26)) %% 26], collapse=""))
}

enc <- function(input, sectorId) {
  temp <- lapply(str_split(input, pattern=""), match, letters)
  temp2 <- sapply(temp, runSeq, sectorId)
  return(temp2)
}

decoded <- c()
for (i in 1:nrow(dt)) {
  result <- enc(dt$splitted[i][[1]], as.integer(dt$sectorId[i]))
  
  decoded <- c(decoded, str_c(result, collapse = ""))
}
dt[,translate:=decoded]
room <- grep("(.*)northpoleobjects(.*)", dt$translate, value = T)
dt[dt$translate == room]$sectorId




