resource "aws_key_pair" "my_key" {
  key_name   = "my-key-pair"
  public_key = file("public_ec2.pub")
}

resource "aws_instance" "public_instance" {
  ami           = "ami-0360c520857e3138f" # Example AMI, update as needed
  instance_type = "t2.micro"
  subnet_id     = aws_subnet.public.id
  security_groups = [aws_security_group.public_sg.id]
  key_name      = aws_key_pair.my_key.key_name
}

resource "aws_instance" "private_instance" {
  ami           = "ami-0360c520857e3138f" # Example AMI, update as needed
  instance_type = "t2.micro"
  subnet_id     = aws_subnet.private.id
  security_groups = [aws_security_group.private_sg.id]
  key_name      = aws_key_pair.my_key.key_name
}

output "public_instance_ip" {
  value = aws_instance.public_instance.public_ip
}

output "private_instance_id" {
  value = aws_instance.private_instance.id
}
# private_instance_id = "i-059d9b38231c3108e"
# public_instance_ip = "98.86.169.39"