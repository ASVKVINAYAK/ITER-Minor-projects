`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    21:07:19 02/07/2021 
// Design Name: 
// Module Name:    N_bit_substractor 
// Project Name: 
// Target Devices: 
// Tool versions: 
// Description: 
//
// Dependencies: 
//
// Revision: 
// Revision 0.01 - File Created
// Additional Comments: 
//
//////////////////////////////////////////////////////////////////////////////////
module N_bit_substractor (a,b,diff,borrow_out);
parameter N=2;
input [N-1:0] a,b;
   output [N-1:0] diff;
   output  borrow_out;
  wire [N-1:0] br;
   genvar i;
   generate 
   for(i=0;i<N;i=i+1)
     begin: generate_N_bit_Substractor
   if(i==0) 
  half_substractor hs(a[0],b[0],diff[0],br[0]);
   else
  full_substractor fs(a[i],b[i],br[i-1],diff[i],br[i]);
     end
  assign borrow_out = br[N-1];
   endgenerate
endmodule
