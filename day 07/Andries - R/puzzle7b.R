# part2

data <- read.table("input7.txt")
names(data) <- "data"
dt <- data.table(data)
dt[,data:=as.character(data)]

pattern.begin <- "^(\\w+)(?=\\[)"
pattern.end <- "(?<=\\])(\\w+)$"
pattern.inner <- "(?<=\\[)(\\w+)(?=\\])"
pattern.outer <- "(?<=\\])(\\w+)(?=\\[)"

get.abas <- function(input) {
  if (is.null(input) || length(input) == 0) {
    return(c())
  }
  splitted <- lapply(X = input, FUN = str_split, "")
  occurences <- sapply(X = splitted, get.abas.single.line)
  return(lapply(occurences, unlist))
}

get.abas.single.line <- function(x) {
  return(lapply(X = x, get.abas.single.element))
}

get.abas.single.element <- function(x) {
  result <- c()
  for (index in 1:(length(x) -2)) {
    if (x[index] == x[index+2] && x[index] != x[index+1]) {
      result <- c(result, paste0(x[index],x[index+1],x[index+2]))
    }
  }
  return(result)
}

reverse.abas <- function(input) {
  lapply(input, reverse.abas.line)
}

reverse.abas.line <- function(line) {
  if (is.null(line)) {
    return(c())
  }
  
  result <- c()
  for (i in 1:length(line)) {
    x <- line[i]
    splitted <- str_split(x, pattern="")[[1]]
    result <- c(result, paste0(splitted[2], splitted[1], splitted[2]))
  }
  return(result)
}

has.reverse <- function(abas, babs) {
  if (is.null(abas[[1]])) {
    return(F)
  }
  return(lapply(abas, has.reverse.line, babs))
}

has.reverse.line <- function(abas.line, babs.line) {
  if (is.null(abas.line)) {
    return(F)
  }
  
  return(lapply(babs.line, has.reverse.element, abas.line))
}
has.reverse.element <- function( babs.el, abas.el) {
  result <- c()
  for (i in 1:length(babs.el)) {
    x <- babs.el[i]
    result <- c(result, x %in% abas.el)
  }
  return(any(result))
}

dt[,begin:=str_extract(data, pattern.begin)]
dt[,end:=str_extract(data, pattern.end)]
dt[,inner:=str_extract_all(data, regex(pattern.inner))]
dt[,outer:=str_extract_all(data, regex(pattern.outer))]
dt[,abas.begin:=get.abas(begin)]
dt[,abas.end:=get.abas(end)]
dt[,abas.outer:=get.abas(outer)]
dt[,abas.inner:=get.abas(inner)]
dt[,babs.inner:=reverse.abas(abas.inner)]
dt <- dt[!sapply(dt$abas.inner, is.null)] # filter NULL
#dt[,valid.begin:=has.reverse(abas.begin, babs.inner), by=1:nrow(dt)]
#dt[,valid.end:=has.reverse(abas.end, babs.inner), by=1:nrow(dt)]
#dt[,valid.outer:=has.reverse(abas.outer, babs.inner), by=1:nrow(dt)]

# fck this sht
result <- c()
for (i in 1:nrow(dt)) {
  abas.end <- unlist(dt[i,]$abas.end)
  abas.begin <- unlist(dt[i,]$abas.begin)
  abas.outer <- unlist(dt[i,]$abas.outer)
  babs <- unlist(dt[i,]$babs.inner)
  
  temp <- any(c(babs %in% abas.end), c(babs %in% abas.begin), c(babs %in% abas.outer))
  result <- c(result, temp)              
}



# testing
dt2 <- dt[c(1390:1396),]
dt2[,valid:=has.reverse(abas.begin, babs.inner), by=1:nrow(dt2)]
