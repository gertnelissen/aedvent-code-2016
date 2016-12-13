library('stringr')
library('data.table')

test <- "abba[mnop]qrst[sqdf]sdfsdfqsdf[dsqf]aez"
data <- read.table("input7.txt")
names(data) <- "data"
dt <- data.table(data)
dt[,data:=as.character(data)]


pattern.begin <- "^(\\w+)(?=\\[)"
pattern.end <- "(?<=\\])(\\w+)$"
pattern.inner <- "(?<=\\[)(\\w+)(?=\\])"
pattern.outer <- "(?<=\\])(\\w+)(?=\\[)"

check.reverse <- function(input) {
  if (is.null(input)) {
    return(FALSE)
  }
  #splitted <- unlist(str_split(input, pattern = ""))
  splitted <- lapply(X = input, FUN = str_split, "")
  occurences <- lapply(X = splitted, check.reverse.single.line)
  return(lapply(occurences, any))
}

check.reverse.single.line <- function(x) {
  return(lapply(X = x, check.reverse.single.element))
}

check.reverse.single.element <- function(x) {
  for (index in 1:(length(x) -3)) {
    if (x[index] == x[index+3] && x[index+1] == x[index+2] && x[index] != x[index+1])  {
      return(T)
    }
  }
  return(F)
}

has.reverse <- check.reverse

dt[,begin:=str_extract(data, pattern.begin)]
dt[,end:=str_extract(data, pattern.end)]
dt[,inner:=str_extract_all(data, regex(pattern.inner))]
dt[,outer:=str_extract_all(data, regex(pattern.outer))]
dt[,valid.begin:=has.reverse(begin)]
dt[,valid.end:=has.reverse(end)]
dt[,invalid.inner:=as.numeric(has.reverse(inner))]
dt[,valid.outer:=has.reverse(outer)]
dt[,valid.nb:=as.numeric(valid.begin) + as.numeric(valid.end) + as.numeric(valid.outer)]
dt[,valid.inner:=(invalid.inner +1)%%2]
dt[,valid.all:=ifelse(valid.nb*valid.inner >= 1, 1, 0)]
print(sum(dt$valid.all))








