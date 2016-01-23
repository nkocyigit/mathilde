package com.nkwh.mathilde.core.data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class MathildeMatrixOps 
{
	private final int CORE_COUNT;
	private final ExecutorService _executerService;
	
	public MathildeMatrixOps()
	{
		CORE_COUNT = Runtime.getRuntime().availableProcessors();
		
		_executerService = Executors.newCachedThreadPool();
	}
	
	public MathildeMatrixFloat plus(MathildeMatrixFloat operand1, MathildeMatrixFloat operand2) throws Exception
	{
		if(operand1.getColCount() == 0 || operand1.getRowCount() == 0 || operand2.getColCount() == 0 || operand2.getRowCount() == 0)
			throw new Exception();
		
		if(operand1.getColCount() != operand2.getColCount() || operand1.getRowCount() != operand2.getRowCount())
			throw new Exception();
		
		int rowCount = operand1.getRowCount();
		int colCount = operand1.getColCount();
		
		float[][] resData = new float[rowCount][colCount];
		float[][] data1 = operand1.getMatrixData();
		float[][] data2 = operand2.getMatrixData();
		
		for(int i = 0; i < rowCount; i++)
		{
			float []resDataI = resData[i];
			float []data1I = data1[i];
			float []data2I = data2[i];
			for(int j = 0; j < colCount; j++)
				resDataI[j] = data1I[j] + data2I[j];
		}
		return new MathildeMatrixFloat(rowCount, colCount, resData);
	}
	
	public MathildeMatrixFloat minus(MathildeMatrixFloat operand1, MathildeMatrixFloat operand2) throws Exception
	{
		if(operand1.getColCount() == 0 || operand1.getRowCount() == 0 || operand2.getColCount() == 0 || operand2.getRowCount() == 0)
			throw new Exception();
		
		if(operand1.getColCount() != operand2.getColCount() || operand1.getRowCount() != operand2.getRowCount())
			throw new Exception();
		
		int rowCount = operand1.getRowCount();
		int colCount = operand1.getColCount();
		
		float[][] resData = new float[rowCount][colCount];
		float[][] data1 = operand1.getMatrixData();
		float[][] data2 = operand2.getMatrixData();
		
		for(int i = 0; i < rowCount; i++)
		{
			float []resDataI = resData[i];
			float []data1I = data1[i];
			float []data2I = data2[i];
			for(int j = 0; j < colCount; j++)
				resDataI[j] = data1I[j] - data2I[j];
		}
		return new MathildeMatrixFloat(rowCount, colCount, resData);
	}
	
	public MathildeMatrixFloat mul(MathildeMatrixFloat operand1, MathildeMatrixFloat operand2) throws Exception
	{		
		int rowCount1 = operand1.getRowCount();
		int colCount1 = operand1.getColCount();
		int rowCount2 = operand2.getRowCount();
		int colCount2 = operand2.getColCount();		
	
		if(colCount1 == 0 || rowCount1 == 0 || colCount2 == 0 || rowCount2 == 0)
			throw new Exception();
		
		if(colCount1 != rowCount2)
			throw new Exception();
		
		float[][] resData = new float[rowCount1][colCount2];
		
		for(int i = 0; i < rowCount1; i++)
		{
			float resDataI[] = resData[i];
			float dataI[] = operand1.getMatrixData()[i];
			
			for (int k = 0; k < rowCount2; k++)
			{
				float opDataK[] = operand2.getMatrixData()[k];
				float valDataIK = dataI[k];
				for(int j = 0; j < colCount2; j++)
					resDataI[j] += valDataIK * opDataK[j];
			}
		}
		return new MathildeMatrixFloat(rowCount1, colCount2, resData);
	}
	
	public MathildeMatrixFloat mulFast(MathildeMatrixFloat operand1, MathildeMatrixFloat operand2) throws Exception
	{		
		int rowCount1 = operand1.getRowCount();
		int colCount1 = operand1.getColCount();
		int rowCount2 = operand2.getRowCount();
		int colCount2 = operand2.getColCount();		
	
		if(colCount1 == 0 || rowCount1 == 0 || colCount2 == 0 || rowCount2 == 0)
			throw new Exception();
		
		if(colCount1 != rowCount2)
			throw new Exception();
		
		float[][] resData = new float[rowCount1][colCount2];
		
		int THREAD_COUNT = CORE_COUNT;
		int perThreadRowCount;
		
		if(rowCount1 < CORE_COUNT)
		{
			THREAD_COUNT = 1;
			perThreadRowCount = rowCount1;
		}
		else
			perThreadRowCount = rowCount1 / CORE_COUNT;
		
		int start = 0;
		int end = 0;
		
		for(int i = 0; i < THREAD_COUNT - 1; i++)
		{
			end = start + perThreadRowCount;
			_executerService.execute(new MulJob(operand1, operand2, resData, start, end));
			start = end;
		}
		
		_executerService.execute(new MulJob(operand1, operand2, resData, start, rowCount1));
		
		_executerService.shutdown();
		boolean finished = _executerService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		
		if(finished)
			return new MathildeMatrixFloat(rowCount1, colCount2, resData);
		
		throw new Exception();
	}
	
	public MathildeMatrixFloat mulScalar(MathildeMatrixFloat operand, float scalarVal) throws Exception
	{
		if(operand.getColCount() == 0 || operand.getRowCount() == 0)
			throw new Exception();
		
		int rowCount = operand.getRowCount();
		int colCount = operand.getColCount();
		
		float[][] resData = new float[rowCount][colCount];
		
		for(int i = 0; i < rowCount; i++)
		{
			float []resDataI = resData[i];
			float []data1I = operand.getMatrixData()[i];
			for(int j = 0; j < colCount; j++)
				resDataI[j] = data1I[j] * scalarVal;
		}
		return new MathildeMatrixFloat(rowCount, colCount, resData);		
	}
	
	public MathildeMatrixFloat transpose(MathildeMatrixFloat operand) throws Exception
	{
		if(operand.getColCount() == 0 || operand.getRowCount() == 0)
			throw new Exception();
		
		int rowCount = operand.getRowCount();
		int colCount = operand.getColCount();
		
		float[][] resData = new float[colCount][rowCount];
		
		for(int i = 0; i < rowCount; i++)
		{
			float []data1I = operand.getMatrixData()[i];
			for(int j = 0; j < colCount; j++)
				resData[j][i] = data1I[j];
		}
		return new MathildeMatrixFloat(colCount, rowCount, resData);
	}
	
	private class MulJob implements Runnable
	{
		private final MathildeMatrixFloat _matrix1;
		private final MathildeMatrixFloat _matrix2;
		private final int _startRow;
		private final int _endRow;
		private final float[][] _bigResData;
		
		public MulJob(MathildeMatrixFloat matrix1,MathildeMatrixFloat matrix2,float[][] resData, int startRow, int endRow) 
		{
			this._matrix1 = matrix1;
			this._matrix2 = matrix2;
			this._startRow = startRow;
			this._endRow = endRow;
			this._bigResData = resData;
		}

		@Override
		public void run() 
		{
			try
			{
				for(int i = _startRow; i < _endRow; i++)
				{
					float resDataI[] = _bigResData[i];
					float dataI[] = _matrix1.getMatrixData()[i];
					
					for (int k = 0; k < _matrix2.getRowCount(); k++)
					{
						float opDataK[] = _matrix2.getMatrixData()[k];
						float valDataIK = dataI[k];
						for(int j = 0; j < _matrix2.getColCount(); j++)				 
							resDataI[j] += valDataIK * opDataK[j];
					}
				}
				//System.out.println(Thread.currentThread().getName()+" i am done ");
			}
			catch(Exception e)
			{	
			}
		}
	}
}
