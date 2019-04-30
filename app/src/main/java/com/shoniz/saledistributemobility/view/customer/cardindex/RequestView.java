package com.shoniz.saledistributemobility.view.customer.cardindex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.Common;

@SuppressLint("SetTextI18n")
public class RequestView extends LinearLayout {
    public int packageValue;
    public int cartonValue;
    public int shortcutValue;
    public int MaxChequeDuration = 240;
    public int chequeDurationValue;
    public long amountValue;
    Button btnRequest, btnSend, btnExistence, btnDelete;
    boolean isFirstFocuse = false;
    private int currentViewId = R.id.txt_shortcut;
    private TextView txtCarton, txtPackage, txtShortcut, txtChequeDuration, txtAmount, txtAmountProduct;
    private KeypadView keypadView;
    private ExistenceListener existenceListener;
    private ProgressBar progressBar;
    private RequestListener requestListener;
    private SendListener sendListener;
    private AmountLongClickListener amountLongClickListener;
    private AmountClickListener amountClickListener;
    private DeleteListener deleteClick;
    private ShortcutLostFocusListener shortcutLostFocusListener;
    private ChequeDurationLostFocusListener chequeDurationLostFocusListener;

    public RequestView(Context context) {
        super(context);
        init(context, null);
    }

    public RequestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RequestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RequestView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public String shortcutString() {
        return txtShortcut.getText().toString();
    }

    public void emptyAll() {
        keypadView.emptyValue();
        packageValue = 0;
        cartonValue = 0;
        shortcutValue = 0;
        amountValue = 0;

        txtCarton.setText("");
        txtPackage.setText("");
        txtShortcut.setText("");
    }

    public void setChequeDuration(int chequeDurationValue) {
        this.chequeDurationValue = chequeDurationValue;
        txtChequeDuration.setText("" + chequeDurationValue);
    }

    public void setRequestAmount(long amountValue) {
        this.amountValue = amountValue;
        txtAmount.setText(Common.getCurrencyFormat(amountValue));
    }

    public void setShortcut(String shortcutValue) {
        this.shortcutValue = Integer.parseInt(shortcutValue);
        txtShortcut.setText("" + shortcutValue);
        if (shortcutValue.length() == 3 && !shortcutValue.startsWith("0")) {
            focusCartonView();
        } else if (shortcutValue.length() == 4) {
            focusPackageView();
        }
    }

    public void setShortcutBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            txtShortcut.setBackground(drawable);
        } else {
            txtShortcut.setBackgroundDrawable(drawable);
        }
    }

    public void setAmountProduct(String amountValue) {
        txtAmountProduct.setText(Common.getCurrencyFormat(amountValue));
    }

    public void setCartonValue(int cartonValue) {
        this.cartonValue = cartonValue;
        txtCarton.setText("" + cartonValue);
    }

    public void setPackageValue(int packageValue) {
        this.packageValue = packageValue;
        txtPackage.setText("" + packageValue);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
//        TypedArray a = context.obtainStyledAttributes(attrs,
//                R.styleable.KeypadView, 0, 0);
        //new ImageView(this).setImageResource(0x7f0800ab);
//            ImageView img = new ImageView(this);
//            img.setImageResource(R.drawable.ic_irr);
//        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            inflater.inflate(R.layout.view_request, this, true);
        } catch (Exception e) {
            e.getMessage();
        }


        btnExistence = findViewById(R.id.btnExistence);
        btnRequest = findViewById(R.id.btnRequest);
        btnSend = findViewById(R.id.btnSend);
        btnDelete = findViewById(R.id.btnDelete);
        progressBar = findViewById(R.id.progressBar);

        txtAmount = findViewById(R.id.txt_amount);
        txtAmountProduct = findViewById(R.id.txt_amount_product);
        txtCarton = findViewById(R.id.txt_carton);
        txtPackage = findViewById(R.id.txt_package);
        txtShortcut = findViewById(R.id.txt_shortcut);
        txtChequeDuration = findViewById(R.id.txt_cheque_duration);
        txtChequeDuration.setTag("0");

//        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_carton_png, null);
//        //Drawable drawable=context.getResources().getDrawable(R.drawable.);
//        txtAmount.setCompoundDrawables(drawable,null,null,null);
        txtShortcut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                keypadView.setValue(txtShortcut.getText().toString());
                setCurrentView(R.id.txt_shortcut);

            }
        });

        txtChequeDuration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                keypadView.setValue(txtChequeDuration.getText().toString());
                setCurrentView(R.id.txt_cheque_duration);
                txtChequeDuration.setTag("1");
            }
        });


        txtCarton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                keypadView.setValue(txtCarton.getText().toString());
                setCurrentView(R.id.txt_carton);
            }
        });

        txtPackage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                keypadView.setValue(txtPackage.getText().toString());
                setCurrentView(R.id.txt_package);
            }
        });

        txtShortcut.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (txtShortcut.getText().toString().length() == 3 && !txtShortcut.getText().toString().startsWith("0")) {
                    String str = "0" + txtShortcut.getText().toString();
                    keypadView.setValue(str);
                    txtShortcut.setText(str);
                } else if (txtShortcut.getText().toString().length() == 4 && txtShortcut.getText().toString().startsWith("0")) {
                    String str = txtShortcut.getText().toString().substring(1);
                    keypadView.setValue(str);
                    txtShortcut.setText(str);

                }
                if (shortcutLostFocusListener != null) {
                    shortcutLostFocusListener.OnLost(RequestView.this);
                }
                return false;
            }
        });


        setCurrentView(R.id.txt_shortcut);
        keypadView = findViewById(R.id.keypadView);
        keypadView.setOnClickApplyListener(new KeypadView.KeypadViewClickApplyListener() {
            @Override
            public void OnClick(KeypadView view) {
                if (currentViewId != R.id.txt_package) {
                    keypadView.setValue(txtPackage.getText().toString());
                    setCurrentView(R.id.txt_package);
                } else {
                    keypadView.setValue(txtCarton.getText().toString());
                    setCurrentView(R.id.txt_carton);
                }

            }
        });

        keypadView.setOnClickNumListener(new KeypadView.KeypadViewClickNumListener() {
            @Override
            public void OnClick(KeypadView view) {

                updateView(view);
            }
        });
        keypadView.setOnClickClearListener(new KeypadView.KeypadViewClickClearListener() {
            @Override
            public void OnClick(KeypadView view) {

                if (isFirstFocuse) {
                    String value = "";
                    ((TextView) findViewById(currentViewId)).setText(value);
                    keypadView.setValue(value);
                }
                updateView(view);
            }
        });


        btnSend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendListener != null) {
                    lostFocusCheque();
                    sendListener.OnClick(RequestView.this);
                }
            }
        });

        btnExistence.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (existenceListener != null) {
                    lostFocusCheque();
                    existenceListener.OnClick(RequestView.this);
                }
            }
        });

        btnRequest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requestListener != null) {
                    lostFocusCheque();
                    requestListener.OnClick(RequestView.this);
                }
            }
        });

        btnRequest.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requestListener != null) {
                    lostFocusCheque();
                    requestListener.OnClick(RequestView.this);
                }
            }
        });

        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClick != null) {


                    lostFocusCheque();
                    deleteClick.OnClick(RequestView.this);
                }
            }
        });

        txtAmount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amountClickListener != null) {
                    amountClickListener.OnClick(RequestView.this);
                }
            }
        });
        txtAmount.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (amountLongClickListener != null) {
                    amountLongClickListener.OnLong(RequestView.this);
                }
                return false;
            }
        });
    }

    public void setProgressBar(boolean flag){
        progressBar.setVisibility(flag ? VISIBLE:INVISIBLE );
    }

    public void setOnSendListener(SendListener listener) {
        this.sendListener = listener;
    }

    public void setOnAmountLongClick(AmountLongClickListener amountLongClickListener) {
        this.amountLongClickListener = amountLongClickListener;
    }

    public void setOnDeleteClick(DeleteListener deleteClick) {
        this.deleteClick = deleteClick;
    }

    public void setOnAmountClick(AmountClickListener amountClickListener) {
        this.amountClickListener = amountClickListener;
    }

    public void setOnRequestListener(RequestListener listener) {
        this.requestListener = listener;
    }

    public void setOnExistenceListener(ExistenceListener listener) {
        this.existenceListener = listener;
    }

    public void setOnShortcutLostFocusListener(ShortcutLostFocusListener listener) {
        this.shortcutLostFocusListener = listener;
    }

    public void setOnChequeDurationLostFocusListener(ChequeDurationLostFocusListener listener) {
        this.chequeDurationLostFocusListener = listener;
    }

    private void updateView(KeypadView view) {
        if (isFirstFocuse && view.getValueString() != null && view.getValueString().length() > 0) {
            String value = view.getValueString().substring(view.getValueString().length() - 1, view.getValueString().length());
            ((TextView) findViewById(currentViewId)).setText(value);
            keypadView.setValue(value);
        }
        isFirstFocuse = false;
        ((TextView) findViewById(currentViewId)).setText(view.getValueString());
        if (currentViewId == R.id.txt_shortcut) {

            shortcutValue = view.getValueInteger();
            if (view.getValueString().length() == 3 && !view.getValueString().startsWith("0")) {
                setCurrentView(R.id.txt_carton);
                view.setValue(txtCarton.getText().toString());
                if (shortcutLostFocusListener != null) {
                    shortcutLostFocusListener.OnLost(RequestView.this);
                }
            } else if (view.getValueString().length() == 4) {
                setCurrentView(R.id.txt_package);
                view.setValue(txtPackage.getText().toString());
                if (shortcutLostFocusListener != null) {
                    shortcutLostFocusListener.OnLost(RequestView.this);
                }
            }
        } else if (currentViewId == R.id.txt_carton) {
            cartonValue = view.getValueInteger();
        } else if (currentViewId == R.id.txt_package) {
            packageValue = view.getValueInteger();
        } else if (currentViewId == R.id.txt_cheque_duration) {
            if (view.getValueString().isEmpty()) {
                chequeDurationValue = 0;
                ((TextView) findViewById(R.id.txt_cheque_duration)).setText("" + 0);
            } else if (view.getValueString().equals(" ")) {
                chequeDurationValue = 0;
                ((TextView) findViewById(R.id.txt_cheque_duration)).setText("" + 0);
            } else if (view.getValueInteger() > MaxChequeDuration) {
                chequeDurationValue = MaxChequeDuration;
                ((TextView) findViewById(R.id.txt_cheque_duration)).setText("" + MaxChequeDuration);
            } else {
                chequeDurationValue = view.getValueInteger();
                ((TextView) findViewById(R.id.txt_cheque_duration)).setText(view.getValueString());
            }
            if (chequeDurationLostFocusListener != null) {
                chequeDurationLostFocusListener.OnLost(RequestView.this);
            }
        }

    }

    public void focusShortcutView() {
        setCurrentView(R.id.txt_shortcut);
    }

    public void focusCartonView() {
        setCurrentView(R.id.txt_carton);
    }

    public void focusPackageView() {
        setCurrentView(R.id.txt_package);
    }

    private void setCurrentView(int resId) {
        currentViewId = resId;

        findViewById(R.id.txt_shortcut).setBackgroundResource(R.drawable.ic_keypad_textfield_default);
        findViewById(R.id.txt_package).setBackgroundResource(R.drawable.ic_keypad_textfield_default);
        findViewById(R.id.txt_carton).setBackgroundResource(R.drawable.ic_keypad_textfield_default);
        findViewById(R.id.txt_cheque_duration).setBackgroundResource(R.drawable.ic_keypad_textfield_default);
        findViewById(resId).setBackgroundResource(R.drawable.ic_keypad_textfield_selected);
        isFirstFocuse = true;
        lostFocusCheque();

    }

    private void lostFocusCheque() {
        if (currentViewId != R.id.txtChequeDuration && txtChequeDuration.getTag().toString().equals("1")) {
            if (shortcutLostFocusListener != null) {
                chequeDurationLostFocusListener.OnLost(RequestView.this);
                txtChequeDuration.setTag("0");
            }
        }
    }

    public void focusCartonText() {
        setCurrentView(R.id.txt_carton);
    }


    public interface RequestListener {
        void OnClick(RequestView view);
    }

    public interface DeleteListener {
        void OnClick(RequestView view);
    }

    public interface ExistenceListener {
        void OnClick(RequestView view);
    }

    public interface SendListener {
        void OnClick(RequestView view);
    }

    public interface ShortcutLostFocusListener {
        void OnLost(RequestView view);
    }

    public interface ChequeDurationLostFocusListener {
        void OnLost(RequestView view);
    }

    public interface AmountLongClickListener {
        void OnLong(RequestView view);
    }

    public interface AmountClickListener {
        void OnClick(RequestView view);
    }

}
