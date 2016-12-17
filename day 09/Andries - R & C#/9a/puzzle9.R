library('stringr')

## helpers

append.decoded <- function(decoded, chs) {
  return (paste0(decoded, chs, collapse=""))
}

getClosingBracketIndex <- function(chars, i) {
  for (j in i:length(chars)) {
    ch <- chars[j]
    if (ch == "x") {
      xi <- j
    }
    if (ch == ")") {
      return(c(xi, j))
    }
  }
  stop()
}

getDim <- function(chars, i, xi, j) {
  a <- as.numeric(paste0(chars[(i+1):(xi-1)], collapse=""))
  b <- as.numeric(paste0(chars[(xi+1):(j-1)], collapse=""))
  return(c(a, b))
}

calculateDecode <- function(chars, dims, j) {
  serie <- paste0(chars[(j+1):(j+dims[1])], collapse="")
  series <- paste0(rep(serie, dims[2]), collapse="")
  return(series)
}

main <- function(data) {
  chars <- str_split(data, pattern="")[[1]]
  fromnext <- 0
  decoded <- ""
  for (i in 1:length(chars)) {
    ch <- chars[i]
    
    if (ch == "(" && i >= fromnext) {
      xij <- getClosingBracketIndex(chars, i)
      dims <- getDim(chars, i, xij[1], xij[2])
      series <- calculateDecode(chars, dims, xij[2])
      decoded <- append.decoded(decoded, series)
      fromnext <- xij[2] + dims[1]
    } else if (i > fromnext) {
      decoded <- paste0(decoded, ch, collapse = "")
    }
  }
  return(decoded)
}


data <- as.character(read.csv("input9.txt", header = F)$V1)
#chars <- str_split(as.character(data$V1), pattern = "")[[1]]

test0 <- "A(1x5)BC"
test1 <- "X(8x2)(3x3)ABCY"
test2 <- "(3x3)XYZ"
test3 <- "(6x1)(1x3)A"
test4 <- "(27x12)(20x12)(13x14)(7x10)(1x12)A"
chars <- str_split(test1, pattern="")[[1]]



