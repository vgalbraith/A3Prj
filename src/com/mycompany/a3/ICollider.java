package com.mycompany.a3;

public interface ICollider
{
	public abstract boolean collidesWith(ICollider otherObject);
	
	public abstract void handleCollision(ICollider otherObject);
}
