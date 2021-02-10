`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    21:04:49 02/07/2021 
// Design Name: 
// Module Name:    full_substractor 
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
module full_substractor( x, y, carry_in, diff, borrow);
input x, y, carry_in;
output diff, borrow;
assign diff= x ^ y ^ carry_in;
assign borrow=(( !x)&&(y ^ carry_in)) || (y && carry_in);
endmodule
