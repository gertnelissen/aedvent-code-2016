library('stringr')

data <- read.table('input6.txt')
vec <- as.character(data[[1]])
splitted <- str_split(vec, pattern="")

getIndex <- function(data, index) {
  return(data[index])
}

arr <- c()
for (i in 1:nchar(vec[1])) {
  lettrs <- lapply(splitted, getIndex, i)
  strng <- paste0(unlist(lettrs), collapse="")
  occurences <- str_count(strng, letters)
  arr <- c(arr, letters[which(occurences %in% min(occurences))]) # part1: max, part2: min
}
print(paste0(arr, collapse=""))