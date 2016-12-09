use strict;
use warnings;
use Data::Dumper;

my $fname = "realInput.txt";
my $compText = "";
my $counter = 0;
my @matches;

open(my $fh, "<$fname");
while(<$fh>){
	chomp;

	$counter += length $_;

	#get matches
	while ($_ =~ /\((\d+)x(\d+)\)/g){
		push @matches, (
			chars => $1,
			rep   => $2, 
			startPat => $-[0], #start pattern (perl build in magic)
			stopPat  => $+[0], #stop pattern (perl build in magic)
			startPosStr => $+[0],
			stopPosStr  => $+[0] + $1,
			additionalPos => -($+[0] - $-[0])
			#^positions that need to be added to account for repetitions, starting with the length of the pattern that is already counted
			#at line 14 ( $counter += length $_; )
		);
	}

	#calculate additional length from repetitions
	for (my $i = 0 ; $i < scalar @matches; $i++){
		my %currMatch = $matches[$i];

		#increment repetitions of other 
		my $j = $i + 1;
		while ($j < scalar @matches and $matches[$j]{startPat} < $currMatch{stopPosStr}){

		}
	}

}



print "found $counter chars!\n";
