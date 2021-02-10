`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    21:03:56 02/07/2021 
// Design Name: 
// Module Name:    half_substractor 
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
module half_substractor( x, y, diff, borrow);
input x, y;
output diff, borrow;
assign diff= x ^ y;
assign borrow= ~x && y;
endmodule
