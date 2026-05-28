package com.norsyst.smpp;

import org.jsmpp.bean.BroadcastSm;
import org.jsmpp.bean.CancelBroadcastSm;
import org.jsmpp.bean.CancelSm;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.QueryBroadcastSm;
import org.jsmpp.bean.QuerySm;
import org.jsmpp.bean.ReplaceSm;
import org.jsmpp.bean.SubmitMulti;
import org.jsmpp.bean.SubmitSm;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.BroadcastSmResult;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.QueryBroadcastSmResult;
import org.jsmpp.session.QuerySmResult;
import org.jsmpp.session.SMPPServerSession;
import org.jsmpp.session.ServerMessageReceiverListener;
import org.jsmpp.session.Session;
import org.jsmpp.session.SubmitMultiResult;
import org.jsmpp.session.SubmitSmResult;

public class TaskServerReceiverProcessor implements ServerMessageReceiverListener {

	@Override
	public DataSmResult onAcceptDataSm(DataSm dataSm, Session source) throws ProcessRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubmitSmResult onAcceptSubmitSm(SubmitSm submitSm, SMPPServerSession source) throws ProcessRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubmitMultiResult onAcceptSubmitMulti(SubmitMulti submitMulti, SMPPServerSession source) throws ProcessRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuerySmResult onAcceptQuerySm(QuerySm querySm, SMPPServerSession source) throws ProcessRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAcceptReplaceSm(ReplaceSm replaceSm, SMPPServerSession source) throws ProcessRequestException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAcceptCancelSm(CancelSm cancelSm, SMPPServerSession source) throws ProcessRequestException {
		// TODO Auto-generated method stub

	}

	@Override
	public BroadcastSmResult onAcceptBroadcastSm(BroadcastSm broadcastSm, SMPPServerSession source) throws ProcessRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onAcceptCancelBroadcastSm(CancelBroadcastSm cancelBroadcastSm, SMPPServerSession source) throws ProcessRequestException {
		// TODO Auto-generated method stub

	}

	@Override
	public QueryBroadcastSmResult onAcceptQueryBroadcastSm(QueryBroadcastSm queryBroadcastSm, SMPPServerSession source) throws ProcessRequestException {
		// TODO Auto-generated method stub
		return null;
	}

}
