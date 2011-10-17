#!/usr/bin/perl
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#
#  Programmer:  Matthew Drennan
# 
#  Function:    1/  Download archived stock data using Yahoo Finance API
#
#               2/  Download real-time stock data using Yahoo Finance API
#
#  Usage:       perl stock_data.pl -h        Downloads archived stock data for companies listed in file 'company_symbols.dat' and 
#                                            puts data into ./historic directory
#
#               perl stock_data.pl           Downloads real-time stock data for companies listed in file 'company_symbols.dat' and
#                                            puts data into ./realtime directory
#
#
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#  Historic Data Options
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#
#   http://www.jot.fm/issues/issue_2007_11/column2.pdf
#  
#  
#   FINDING THE DATA 
#  Finding the data, on-line, and free, is a necessary first step toward this type of data 
#  mining. Quotes have been available, for years, from Yahoo. We can obtain the quotes, 
#  using comma-separated values (CSV) by constructing a URL and entering it into a 
#  browser. For example: 
#  http://table.finance.yahoo.com/table.csv?s=IBM&a=00&b=2&c=1800&d=04&e=8&f=2005&g=v&ignore=.csv              DATA MINING HISTORIC STOCK QUOTES IN JAVA 
#  
#  18  JOURNAL OF OBJECT TECHNOLOGY  VOL. 6, NO. 10 
#  This creates an output on the screen that looks like: 
#  Date,Dividends 
#  2005-05-06,0.200000 
#  2005-02-08,0.180000 
#  2004-11-08,0.180000 
#  2004-08-06,0.180000 
#  2004-05-06,0.180000 
#   …
#  Thus, we are able to obtain a listing of all the dividends that  IBM paid, between two 
#  given dates. As another example, consider the week leading up to the IPhone release from 
#  Apple: 
# 
#  http://ichart.finance.yahoo.com/table.csv?s=aapl&a=5&b=22&c=2007&d=5&e=29&f=2007&g=d&ignore=.csv  
#  
#  the quotes are returned in the form: 
#  Date,Open,High,Low,Close,Volume,Adj Close 
#  2007-06-29,121.97,124.00,121.09,122.04,40513400,122.04 
#  2007-06-28,122.36,122.49,120.00,120.56,29933700,120.56 
#  2007-06-27,120.61,122.04,119.26,121.89,34810600,121.89 
#  2007-06-26,123.98,124.00,118.72,119.65,47913500,119.65 
#  2007-06-25,124.19,125.09,121.06,122.34,34478700,122.34 
#  2007-06-22,123.85,124.45,122.38,123.00,22567000,123.00 
#  The URL is decoded as: 
#  s - ticker symbol 
#  a - start month  
#  b - start day 
#  c - start year 
#  d - end month 
#  e - end day 
#  f - end year 
#  g - resolution (e.g. 'd' is daily, 'w' is weekly, 'm' is monthly)
#
#



#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#  Real-Time Data Options
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#
#  a	 Ask	 														l	 Last Trade (With Time)	
#  a2	 Average Daily Volume											l1	 Last Trade (Price Only) 
#  a5	 Ask Size														l2	 High Limit	
#  b	 Bid	 														l3	 Low Limit
#  b2	 Ask (Real-time)	 											m	 Day's Range
#  b3	 Bid (Real-time)												m2	 Day's Range (Real-time)	  mm3m8m4m6
#  b4	 Book Value	 													m3	 50-day Moving Average
#  b6	 Bid Size	 													m4	 200-day Moving Average
#  c	 Change & Percent Change										m5	 Change From 200-day Moving Average	
#  c1	 Change	 														m6	 Percent Change From 200-day Moving Average	 
#  c3	 Commission	 													m7	 Change From 50-day Moving Average
#  c6	 Change (Real-time)												m8	 Percent Change From 50-day Moving Average
#  c8	 After Hours Change (Real-time)									n	 Name	
#  d	 Dividend/Share	 												n4	 Notes
#  d1	 Last Trade Date												o	 Open	
#  d2	 Trade Date	 													p	 Previous Close	
#  e	 Earnings/Share	 												p1	 Price Paid
#  e1	 Error Indication (returned for symbol changed)					p2	 Change in Percent
#  e7	 EPS Estimate Current Year	 									p5	 Price/Sales
#  e8	 EPS Estimate Next Year	 										p6	 Price/Book
#  e9	 EPS Estimate Next Quarter										q	 Ex-Dividend Date
#  f6	 Float Shares	 												r	 P/E Ratio
#  g	 Day's Low	 													r1	 Dividend Pay Date
#  h	 Day's High														r2	 P/E Ratio (Real-time)	
#  j	 52-week Low	 												r5	 PEG Ratio
#  k	 52-week High	 												r6	 Price/EPS Estimate Current Year
#  g1	 Holdings Gain Percent											r7	 Price/EPS Estimate Next Year
#  g3	 Annualized Gain					 							s	 Symbol	
#  g4	 Holdings Gain	 												s1	 Shares Owned
#  g5	 Holdings Gain Percent (Real-time)								s7	 Short Ratio
#  g6	 Holdings Gain (Real-time)	 									t1	 Last Trade Time
#  i	 More Info	 													t6	 Trade Links
#  i5	 Order Book (Real-time)											t7	 Ticker Trend	
#  j1	 Market Capitalization	 										t8	 1 yr Target Price
#  j3	 Market Cap (Real-time)	 										v	 Volume
#  j4	 EBITDA															v1	 Holdings Value
#  j5	 Change From 52-week Low	 									v7	 Holdings Value (Real-time)	 
#  j6	 Percent Change From 52-week Low	 							w	 52-week Range
#  k1	 Last Trade (Real-time) With Time								w1	 Day's Value Change
#  k2	 Change Percent (Real-time)	 									w4	 Day's Value Change (Real-time)	 
#  k3	 Last Trade Size	 											x	 Stock Exchange
#  k4	 Change From 52-week High										y	 Dividend Yield
#  k5	 Percebt Change From 52-week High
#
#
#
#
#
#
#
#  my $URL = 'http://finance.yahoo.com/d/quotes.csv?s=XOM+BBDb.TO+JNJ+MSFT&f=sd1l1yrn';
#  my $URL = 'http://finance.yahoo.com/d/quotes.csv?s=IYW&f=snd1l1yr';
#  my $URL       =  "http://finance.yahoo.com/d/quotes.csv?s=@symbols&f=sd1l1yrr5s7mm3m8m4m6n";
#
#	 
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

  use LWP::Simple;
  use LWP::UserAgent;
  use HTTP::Request;
  
  
  system ('clear');

  
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#  Determine which data type (realtime or historic) to download
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
   
   chdir($ARGV[1]);

   &time;

   &symbols;                                                            # read in stock symbols

   

   if ($ARGV[0] eq '-h') 
   
   { 
	   
      print "\n  Will retrieve historic data...\n\n"; 
   	   
      &historic;
	   
   }

   else
   
   { 
	   
	  print "\n  Will retrieve real-time data...\n\n"; 
     
      &realtime; 
	   
   }
   
  exit;
  

  
  
  
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#  Find Current Time
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
  
sub time {

  $timezone = 8;
  
  $YR = `date -u +%Y`; chop ($YR);
  $MO = `date -u +%m`; chop ($MO);
  $DY = `date -u +%d`; chop ($DY);
  $HR = `date -u +%H`; chop ($HR);
  
  $end_year  = `date -u --date='$YR-$MO-$DY $HR:00:00 UTC  -$timezone hours' +%Y`;  chop ($end_year);
  $end_month = `date -u --date='$YR-$MO-$DY $HR:00:00 UTC  -$timezone hours' +%m`;  chop ($end_month);
  $end_day   = `date -u --date='$YR-$MO-$DY $HR:00:00 UTC  -$timezone hours' +%d`;  chop ($end_day);
  
}
 
    
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#  Read in stock symbols
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
  
sub symbols {  

   open nasdaq, './company_symbols.dat' or die $!;   

   $line = <nasdaq>;  # header line
	      
   foreach $line (<nasdaq>) {

      chomp ($line);                    # remove the newline from $line.
                     
      @vars  = split(/\|/, $line);      # do line-by-line processing.
                 
      push(@s, $vars[0]); #   push(@symbols, '+');
        
               
   }                   
                 
   #print " listings = @s  \n\n";   
   
   print " number of listings = $#s\n\n";
 
} #(sub)
  
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#  Historic Data Downoad
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
  
sub historic {  
	  
   print "\n  starting to retrieve historic data...\n\n\n"; 

   if (! -d "./historic") { mkdir ("./historic"); } 
   
   chdir ("./historic");
   
   
   foreach $symbol (@s) {
   
      print " $symbol ";
        	
      $URL       =  "http://ichart.finance.yahoo.com/table.csv?s=$symbol&a=1&b=1&c=1970&d=$MO&e=$DY&f=$YR&g=d&ignore=.csv"; 
   
      my $agent     =  LWP::UserAgent->new(env_proxy => 1,keep_alive => 1, timeout => 30);
      my $header    =  HTTP::Request->new(GET => $URL);
      my $request   =  HTTP::Request->new('GET', $URL, $header);
      my $response  =  $agent->request($request);

     
# Check the outcome of the response

      if ($response->is_success) 
   
      {
	   
#        print "URL:$URL\nHeaders:\n";
      
#        print $response->headers_as_string;
      
#        print "\nContent:\n";
         
         open FILE, "> ./$symbol.dat" or die $!; 
         
         print FILE $response->as_string;
   
         close (FILE);        
          
             
      }
   
      elsif ($response->is_error) 
   
      {
	   
         print "Error:$URL\n";
      
         print $response->error_as_HTML;
      
      }
 
   }   
        
} #(sub)	
	
	



#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#  Real-time Data Download
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

sub realtime {  

   if (! -d "./realtime") { mkdir ("./realtime"); }	
	
	
	
#    [0]   s 	        Symbol
#    [1]   d1           Last Trade Date
#    [2]   l1           Last Trade (Price Only)  
#    [3]   p2           Change in Percent
#    [4]   y            Dividend Yield
#    [5]   r
#    [6]   r5
#    [7]   s7
#    [8]   m
#    [9]   m3
#   [10]   m8
#   [11]   m4    
#   [12]   m6    
#   [13]   n   
#   [14]   t8           1 yr Target Price
#   [15]   v			Volume (daily) 
#   [16]   a2 			Average Daily Volume
 


  $tag = 'sd1l1p2yrr5s7mm3m8m4m6nt8va2';
  
  
 
  print "\n\n  tag  =  $tag\n";
  
  
   
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
   

#::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::   
#  Output file header 
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 

   unlink ("./quote2.dat");  # Remove old output file in the event it exits


   
 #  open (FILTER, '>> /home/model/AlgoTrading/filter.dat');
 #  open (FILTER2, '>> /home/model/AlgoTrading/filter2.dat');
   open (OF, '>> ./quote2.dat');
  
   printf OF  "%8s",  'Symbol';
   printf OF  "%5s", '     ';
   printf OF  "%-18s",  'Company';
   printf OF  "%15s",  'Date';
   printf OF  "%9s",  'Price';
   printf OF  "%9s",  'Change%'; 
   printf OF  "%9s",  'Yield';
   printf OF  "%9s",  'P/E';
   printf OF  "%9s",  'PEG';
   printf OF  "%9s",  'Short';
   printf OF  "%18s",  'Range';
   printf OF  "%9s",  '50Davg'; 
   printf OF  "%9s",  '50DChng';  
   printf OF  "%9s",  '200Davg'; 
   printf OF  "%9s",  '200DChng';  
   printf OF  "%9s",  '1Ytarget';
   printf OF  "%14s",  'Volume'; 
   printf OF  "%14s",  'AvgVol.';            
   printf OF  "\n\n",;  


#::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::   
#   
#::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


  for ($N = 0; $N < 8000; $N=$N+100) {
	  
    print "  N = $N\n\n";	  
 
    
    open FILE, "> ./quote.dat" or die $!;
  
    $temp0 = "$s[$N]+$s[$N+1]+$s[$N+2]+$s[$N+3]+$s[$N+4]+$s[$N+5]+$s[$N+6]+$s[$N+7]+$s[$N+8]+$s[$N+9]+";
    $temp1 = "$s[$N+10]+$s[$N+11]+$s[$N+12]+$s[$N+13]+$s[$N+14]+$s[$N+15]+$s[$N+16]+$s[$N+17]+$s[$N+18]+$s[$N+19]+";
    $temp2 = "$s[$N+20]+$s[$N+21]+$s[$N+22]+$s[$N+23]+$s[$N+24]+$s[$N+25]+$s[$N+26]+$s[$N+27]+$s[$N+28]+$s[$N+29]+";
    $temp3 = "$s[$N+30]+$s[$N+31]+$s[$N+32]+$s[$N+33]+$s[$N+34]+$s[$N+35]+$s[$N+36]+$s[$N+37]+$s[$N+38]+$s[$N+39]+";  
    $temp4 = "$s[$N+40]+$s[$N+41]+$s[$N+42]+$s[$N+43]+$s[$N+44]+$s[$N+45]+$s[$N+46]+$s[$N+47]+$s[$N+48]+$s[$N+49]+";  
    $temp5 = "$s[$N+50]+$s[$N+51]+$s[$N+52]+$s[$N+53]+$s[$N+54]+$s[$N+55]+$s[$N+56]+$s[$N+57]+$s[$N+58]+$s[$N+59]+";     
    $temp6 = "$s[$N+60]+$s[$N+61]+$s[$N+62]+$s[$N+63]+$s[$N+64]+$s[$N+65]+$s[$N+66]+$s[$N+67]+$s[$N+68]+$s[$N+69]+";     
    $temp7 = "$s[$N+70]+$s[$N+71]+$s[$N+72]+$s[$N+73]+$s[$N+74]+$s[$N+75]+$s[$N+76]+$s[$N+77]+$s[$N+78]+$s[$N+79]+";
    $temp8 = "$s[$N+80]+$s[$N+81]+$s[$N+82]+$s[$N+83]+$s[$N+84]+$s[$N+85]+$s[$N+86]+$s[$N+87]+$s[$N+88]+$s[$N+89]+";     
    $temp9 = "$s[$N+90]+$s[$N+91]+$s[$N+92]+$s[$N+93]+$s[$N+94]+$s[$N+95]+$s[$N+96]+$s[$N+97]+$s[$N+98]+$s[$N+99]";  
    
          
       
       
      
    $URL       =  "http://finance.yahoo.com/d/quotes.csv?s=$temp0$temp1$temp2$temp3$temp4$temp5$temp6$temp7$temp8$temp9&f=$tag";  
               
      my $agent     =  LWP::UserAgent->new(env_proxy => 1,keep_alive => 1, timeout => 30);
      my $header    =  HTTP::Request->new(GET => $URL);
      my $request   =  HTTP::Request->new('GET', $URL, $header);
      my $response  =  $agent->request($request);

     
# Check the outcome of the response

   if ($response->is_success) {
	   
      print "URL:$URL\nHeaders:\n";
      
      print $response->headers_as_string;
      
      print "\nContent:\n";
      
      print FILE $response->as_string;
      
   }
   
   elsif ($response->is_error) {
	   
      print "Error:$URL\n";
      
      print $response->error_as_HTML;
      
   }
  
   
   
   
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#    
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

    open FILE, "./quote.dat" or die $!;
   
   
   foreach (1..11) {
	
      $line = <FILE>;
	      
   }
   
   
   foreach $line (<FILE>) {

      chomp ($line);              # remove the newline from $line.


      
      
#   $line =~ s/"$//g;     # (as travs69 suggested)  
#   $line =~ s/\"\r?\n$//g;    
#    print "$line\n";    
#    print "@vars\n";
#    printf webfile "%7s%1s%2s%1s%2s%1s%2s%1s%2s%3s%9.2f%9d\n",  $SURFACE_TIME[0],'-',$SURFACE_TIME[1],     
#     $vars[0] =  split('"',$vars[0]);
       
  
      ( @vars ) = split(',',$line);    ## do line-by-line processing.
  
      $symbol       =  $vars[0];       $symbol =~ s/\"/ /;    # remove quotes
                                       $symbol =~ s/\"/ /;    # remove quotes
                                       $symbol =~ s/^\s+//;   #remove leading spaces
                                       $symbol =~ s/\s+$//;   #remove trailing spaces 
                                       
                                       
                                       
      $date         =  $vars[1];       $date =~ s/\"/ /;    # remove quotes
                                       $date =~ s/\"/ /;    # remove quotes    
                                       $date =~ s/^\s+//;   #remove leading spaces
                                       $date =~ s/\s+$//;   #remove trailing spaces   
          
      $last_price      =  $vars[2]; 
      
      $percent_change  =  $vars[3];    $percent_change =~ s/\"/ /;    # remove quotes  
                                       $percent_change =~ s/\"/ /;    # remove quotes  
                                       $percent_change =~ s/^\s+//;   #remove leading spaces
                                       $percent_change =~ s/\s+$//;   #remove trailing spaces  
         
                 
      $yield           =  $vars[4];
      
      $pe_ratio        =  $vars[5]; 
       
      $peg_ratio       =  $vars[6];  
                
      $short        =  $vars[7];
      
      $trade_range  =  $vars[8];
      
      $avg_50D      =  $vars[9];
      
      $change_50D   =  $vars[10]; 
      
      $avg_200D     =  $vars[11];  
           
      $change_200D  =  $vars[12];   
          
      $name         =  $vars[13];      $name =~ s/\"/ /;    # remove quotes  
                                       $name =~ s/\"/ /;    # remove quotes  
                                       $name =~ s/^\s+//;   #remove leading spaces
                                       $name =~ s/\s+$//;   #remove trailing spaces
 
    
      $target_price  =  $vars[14];                                   
                                       
      $volume  =  $vars[15];
      
      $volume_avg  =  $vars[16];  
                                              
              
      printf OF  "%8s",       $symbol;
      printf OF  "%4s",       '     ';        
      printf OF  "%-18s",     $name;      
      printf OF  "%15s",      $date; 
      printf OF  "%9.2f",     $last_price; 
      printf OF  "%9.2f",     $percent_change; 
      printf OF  "%9.2f",     $yield;  
      printf OF  "%9.2f",     $pe_ratio;
      printf OF  "%9.2f",     $peg_ratio;
      printf OF  "%9.2f",     $short;
      printf OF  "%18s",      $trade_range; 
      printf OF  "%9.2f",     $avg_50D;       
      printf OF  "%9.2f",     $change_50D;      
      printf OF  "%9.2f",     $avg_200D;      
      printf OF  "%9.2f",     $change_200D;
      printf OF  "%9.2f",     $target_price;    
      
      printf OF  "%14d",     $volume;
      printf OF  "%14d",     $volume_avg;
      
       
      
      printf OF  "\n",;
 
      
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
# Closing price BELOW 50D average and ABOVE 200D average   
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::      
      
  if (($last_price<$avg_50D)&&($last_price>$avg_200D)&&($volume_avg>100000)) 
  
  { 
     printf FILTER  "%8s",       $symbol;
     printf FILTER  "%4s",       '     ';        
     printf FILTER  "%-18s",     $name;      
     printf FILTER  "%15s",      $date; 
     printf FILTER  "%9.2f",     $last_price; 
     printf FILTER  "%9.2f",     $percent_change; 
     printf FILTER  "%9.2f",     $yield;  
     printf FILTER  "%9.2f",     $pe_ratio;
     printf FILTER  "%9.2f",     $peg_ratio;
     printf FILTER  "%9.2f",     $short;
     printf FILTER  "%18s",      $trade_range; 
     printf FILTER  "%9.2f",     $avg_50D;       
     printf FILTER  "%9.2f",     $change_50D;      
     printf FILTER  "%9.2f",     $avg_200D;      
     printf FILTER  "%9.2f",     $change_200D;
     printf FILTER  "%9.2f",     $target_price;          
     printf FILTER  "%14d",     $volume;
     printf FILTER  "%14d",     $volume_avg;             
     printf FILTER  "\n",;                
  }   
  
  
 #:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
# Closing price BELOW 50D average and ABOVE 200D average   
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::      
      
  if (($last_price<$avg_50D)&&($last_price<$avg_200D)&&($volume_avg>100000)) 
  
  { 
     printf FILTER2  "%8s",       $symbol;
     printf FILTER2  "%4s",       '     ';        
     printf FILTER2  "%-18s",     $name;      
     printf FILTER2  "%15s",      $date; 
     printf FILTER2  "%9.2f",     $last_price; 
     printf FILTER2  "%9.2f",     $percent_change; 
     printf FILTER2  "%9.2f",     $yield;  
     printf FILTER2  "%9.2f",     $pe_ratio;
     printf FILTER2  "%9.2f",     $peg_ratio;
     printf FILTER2  "%9.2f",     $short;
     printf FILTER2  "%18s",      $trade_range; 
     printf FILTER2  "%9.2f",     $avg_50D;       
     printf FILTER2  "%9.2f",     $change_50D;      
     printf FILTER2  "%9.2f",     $avg_200D;      
     printf FILTER2  "%9.2f",     $change_200D;
     printf FILTER2  "%9.2f",     $target_price;          
     printf FILTER2  "%14d",     $volume;
     printf FILTER2  "%14d",     $volume_avg;             
     printf FILTER2  "\n",;                
  }    
  
  
 
      
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#    
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::    
                       
   }  #(foreach, line)     
      
            
     close (FILE);

  
     
   }  #(for)  
     
     
   close (OF);  
 
   
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
#    
#:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
   
   unlink ("./quote.dat");    # Remove old output file in the event it exits
   
   system ("mv ./quote2.dat   ./realtime/rt_$end_year-$end_month-$end_day.dat"); 
   
#   system ("mv /home/model/AlgoTrading/filter.dat   /home/www/html/investing/algotrade/filter_$end_year-$end_month-$end_day.dat");
#   system ("mv /home/model/AlgoTrading/filter2.dat  /home/www/html/investing/algotrade/filter2_$end_year-$end_month-$end_day.dat");  
     
  
} #sub

 
exit;







