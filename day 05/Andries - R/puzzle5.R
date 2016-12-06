library("data.table")
library("foreach")
library("doParallel")
library("openssl")

# part 1 -> use pattern = ^[0]{5}
# part 2 -> use pattern = ^[0]{5}[0-7]{1}
main <- function() {
  ilist <- seq(1,30000000,1000000)
  ss <- list()
  for (i in 1:(length(ilist)-1)) {
    ss[[i]] <- seq(ilist[i], ilist[i+1])
  }
  
  cl <- makeCluster(6) # use all my 6 cores!
  registerDoParallel(cl)
  out <- 
    foreach(sequence = ss, .packages = c('data.table', 'openssl'), .inorder = TRUE) %dopar% {
      input <- "cxdnnyjw"
      a<-sapply(input, paste0, sequence)[,1]
      b<-md5(a)
      pattern0 <- "^[0]{5}[0-7]{1}"
      return(grep(pattern0, x=b, value=T))
    }
  stopCluster(cl)
  print(out)
  
  backup <- out
  results <- unlist(out)
}

system.time(main())

# [1] "000007c827126c81fa664211693f2540" < 7
# [2] "000007880153f1b804481a39a6d2e86a"
# [3] "00000096164643e2e0fbf5a91bfd7f06" < 0
# [4] "000006ec13bc03b597beee4fa9352176" < 6
# [5] "00000426f1cd2f19a38114170b33c5ef" < 4
# [6] "000007d5ead928e4d0aa9dc516f16f42" 
# [7] "000007937c1b2a62ef8d18bab5f52a5d"
# [8] "000002966111d9b5c057ab99802ff414" < 2
# [9] "000007a798e0c990883b19a55ce03092"
# [10] "000003810329edb2a26e9ccf602fd5c1" < 3
# [11] "000006a809b3a491b43a2e3b9987dd91"
# [12] "00000718147992fe5089c79072cb3b97"
# [13] "000004ea66bd2ea0b40182aa607f8848"
# [14] "000005829911142c7c1592863f4fd438" < 5
# [15] "0000042318414a15d88da98dd819a220"
# [16] "000004cf633c6ad4fbe55232460c4dc6"
# [17] "0000056f9e076bce2a2058086c415d39"
# [18] "000003045ebaa06c1f1cb74f0c56bfec"
# [19] "000004f24f1bb351f29a363bf61963bd"
# [20] "0000019ba65feb3f3b74f37b29ce1481" < 1
# [21] "000004fb5c504e08e9ee2f9a679adc29"
# [22] "000007e2be8a8305a2a880f656d5daf7"

# -> 999828ec
